package hellojpa;

import hellojpa.inheritance.Movie;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class Run {
    // 애플리케이션 로딩 시점에 딱 하나 (DB 당)
    private EntityManagerFactory emf;
    // 요청 단위마다 만들어주어야함
    // 쓰레드 간 공유 X
    private EntityManager em;
    // JPA 모든 작업은 트랜잭션 안에서 일어난다
    private EntityTransaction tx;

    public void run() {
        emf = Persistence.createEntityManagerFactory("jpa-hello");
        em = emf.createEntityManager();
        tx = em.getTransaction();

        tx.begin();

        try {
            detachedProxy();
            tx.commit();
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }


    // 준영속 상태와 프록시
    void proxyUtils() {
        Member member1 = new Member();
        em.persist(member1);
        em.flush();
        em.clear();

        Member memberRef = em.getReference(Member.class, member1.getId()); // 프록시
        // 1.
        System.out.println("getPersistenceUnitUtil = " + emf.getPersistenceUnitUtil().isLoaded(memberRef));
        // 2.
        System.out.println("getUsername = " + memberRef.getUsername());
        // 3.
        Hibernate.initialize(memberRef);
    }

    // 준영속 상태와 프록시
    void detachedProxy() {
        Member member1 = new Member();
        em.persist(member1);
        em.flush();
        em.clear();

        Member memberRef = em.getReference(Member.class, member1.getId()); // 프록시
        System.out.println("memberRef.getClass() = " + memberRef.getClass());

        em.detach(memberRef); // = em.close(), em.clear()

        // 에러 발생
        System.out.println("memberRef = " + memberRef.getUsername());
    }

    // 프록시를 조회했을 때, 영속성 컨텍스트에 이미 있다면 실제 엔티티 반환 (반대도 성립)
    void whenProxyAlreadyExist() {
        Member member1 = new Member();
        em.persist(member1);
        em.flush();
        em.clear();

        Member member = em.find(Member.class, member1.getId());
        System.out.println("member.getClass() = " + member.getClass()); // Member
        Member memberRef = em.getReference(Member.class, member1.getId());
        System.out.println("memberRef.getClass() = " + memberRef.getClass()); // Member
    }

    // 프록시 타입 비교
    void proxyType() {
        Member member1 = new Member();
        em.persist(member1);
        Member member2 = new Member();
        em.persist(member2);

        em.flush();
        em.clear();

        Member m1 = em.find(Member.class, member1.getId());
        Member m2 = em.find(Member.class, member2.getId());

        // true
        System.out.println("m1 == m2 = " + (m1 == m2));
        // true
        System.out.println("m1 == m2 = " + (m1.getClass() == m2.getClass()));

        Member m3 = em.getReference(Member.class, member2.getId());
        // false
        System.out.println("m3 == m2 = " + (m3 == m2));
        // false
        System.out.println("m3 == m2 = " + (m3.getClass() == m2.getClass()));
        // true
        System.out.println("m3 == m2 = " + (m3 instanceof Member));
    }

    // 프록시
    void proxy() {
        Member member = new Member();
        member.setUsername("hi");
        em.persist(member);

        em.flush();
        em.clear();

        // getReference 시점에는 쿼리하지 않는다.
        Member findMember = em.getReference(Member.class, member.getId());
        System.out.println("member = " + member.getUsername());
    }

    // MappedSuperClass
    void mappedSuperClassTest() {
        Member member = new Member();
        member.setCreatedBy("kim");
        member.setCreatedDate(LocalDateTime.now());

        em.persist(member);

        em.flush();
        em.clear();
    }

    // 상속관계
    void inheritance() {
        Movie movie = new Movie();
        movie.setDirector("aaa");
        movie.setActor("bbb");
        movie.setName("바람과함께 사라지다.");
        movie.setPrice(10000);

        em.persist(movie);

        em.flush();
        em.clear();

        em.find(Movie.class, movie.getId());
    }

    // 1:N
    void oneToMany() {
        Member member = new Member();
        member.setUsername("member1");
        em.persist(member);

        Team team = new Team();
        team.setName("team1");
        team.getMembers().add(member);

        em.persist(team);
    }


    // 연관관계
    void mapping() {
        Team team = new Team();
        team.setName("TeamA");
        em.persist(team);
        System.out.println("===================");
        Member member = new Member();
        member.setUsername("Member1");
        member.setTeam(team);
        em.persist(member);
        System.out.println("===================");
        // 테스트 시
        // 영속성 컨텍스트를 비우고 실제 쿼리를 보고 싶을 때
        em.flush();
        em.clear();

        // 단방향
        Member findMember = em.find(Member.class, member.getId());
        Team findTeam = findMember.getTeam();
        System.out.println("findTeam.getName() = " + findTeam.getName());

        // 양방향 연관관계
        List<Member> members = findMember.getTeam().getMembers();
        System.out.println("===================");

        // 양쪽에 값을 넣어줘야 하는이유
        team.getMembers().add(member);
        Team findTeam2 = em.find(Team.class, team.getId()); // 1차 캐시
        List<Member> members2 = findTeam2.getMembers();

        System.out.println("===================");
        for (Member m : members2) {
            System.out.println("m.getUsername() = " + m.getUsername());
        }
        System.out.println("===================");
    }

    // 객체를 테이블에 맞춰 설계하면 일어나는일
    // 객체지향적이지 않다.
    // 협력관계를 만들 수 없다.
    void tablized() {
        Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        Member member = new Member();
        member.setUsername("Member1");
//        member.setTeamId(team.getId());
        em.persist(member);

        Member findMember = em.find(Member.class, member.getId());

//        Long findTeamId = findMember.getTeamId();
//        Team findTeam = em.find(Team.class, findTeamId);
    }

    // 준영속
    void detachedPersistence() {
        Member member = em.find(Member.class, 1L);
        member.setUsername("qwer");

        em.detach(member);
        // 준영속 상태기 때문에 업데이트가 일어나지 않음
    }

    // 플러시 강제 호출
    void forceFlush() {
        Member member = new Member();
        member.setId(200L);
        member.setUsername("member200");
        em.persist(member);
        // 플러시 강제 호출
        em.flush();
    }

    // 변경감지
    void changeDetection() {
        // 영속 엔티티 조회
        Member member = em.find(Member.class, 1L);
        // 영속 엔티티 수정
        member.setUsername("helllooo");
        // em.persist(member) 필요없음
    }

    // 트랜잭션을 지원하는 쓰기 지연
    void transactionalDelayedWrite() {
        Member member1 = new Member();
        member1.setId(102L);
        member1.setUsername("member1");
        Member member2 = new Member();
        member2.setId(102L);
        member2.setUsername("member2");

        em.persist(member1);
        em.persist(member2);
    }

    // 영속성 컨텍스트 1차캐시
    void persistenceContextFirstCache() {
        Member a = em.find(Member.class, 1L); // 쿼리 생성됨
        Member b = em.find(Member.class, 1L); // 쿼리 생성X (1차 캐시조회)
        System.out.println(a == b);
        tx.commit();
    }

    // JPQL 조회
    // 'm' JPA의 조회 대상은 객체
    void jpqlSelect() {
        List<Member> result = em.createQuery("select m from Member as m", Member.class)
                .setFirstResult(1) // 1번부터 offset
                .setMaxResults(10) // 10개 limit
                .getResultList();

        for (Member member : result) {
            System.out.println("member.getName() = " + member.getUsername());
        }
    }

    // 삭제
    void delete() {
        Member findMember = em.find(Member.class, 1L);
        findMember.setUsername("helloJPA");
    }

    // 수정
    void update() {
        Member findMember = em.find(Member.class, 1L);
        findMember.setUsername("helloJPA");
    }

    // 조회
    void insert() {
        Member findMember = em.find(Member.class, 1L);
        System.out.println("findMember.getId() = " + findMember.getId());
        System.out.println("findMember.getName() = " + findMember.getUsername());
    }


}
