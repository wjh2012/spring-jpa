package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        System.out.println("hello world");

        // 애플리케이션 로딩 시점에 딱 하나 (DB 당)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-hello");

        // 요청 단위마다 만들어주어야함
        // 쓰레드 간 공유 X
        EntityManager em = emf.createEntityManager();
        // JPA 모든 작업은 트랜잭션 안에서 일어난다
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        // 삽입
//        try {
//            // 비영속
//            MemberSample memberSample = new MemberSample();
//            // 영속      
//            em.persist(memberSample);
//            tx.commit();
//            
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }

        // 조회
//        try {
//            Member findMember = em.find(Member.class, 1L);
//            System.out.println("findMember.getId() = " + findMember.getId());
//            System.out.println("findMember.getName() = " + findMember.getName());
//            
//            tx.commit();
//            
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }        

        // 수정
//        try {
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("helloJPA");
//            
//            tx.commit();
//            
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }

        // 삭제
//        try {
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("helloJPA");
//            
//            tx.commit();
//            
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }

        // JPQL 조회
        // 'm' JPA의 조회 대상은 객체
//        List<Member> result = em.createQuery("select m from Member as m", Member.class)
//            .setFirstResult(1) // 1번부터 offset
//            .setMaxResults(10) // 10개 limit
//            .getResultList();
//        
//        for (Member member : result) {
//            System.out.println("member.getName() = " + member.getName());
//        }

        // 영속성 컨텍스트 1차캐시
//        try {
//            Member a = em.find(Member.class, 1L); // 쿼리 생성됨
//            Member b = em.find(Member.class, 1L); // 쿼리 생성X (1차 캐시조회)
//            System.out.println(a == b);
//            tx.commit();
//
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }

        // 트랜잭션을 지원하는 쓰기 지연
//        try {
//            Member member1 = new Member(101L, "member1");
//            Member member2 = new Member(102L, "member2");
//            
//            em.persist(member1);
//            em.persist(member2);
//            
//            tx.commit();
//
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }        

        // 변경감지
//        try {
//            // 영속 엔티티 조회
//            Member member = em.find(Member.class, 1L);
//            // 영속 엔티티 수정
//            member.setName("helllooo");
//            // em.persist(member) 필요없음
//            tx.commit();
//
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }

        // 플러시 강제 호출
//        try {
//            Member member = new Member(200L, "member200");
//            em.persist(member);
//            // 플러시 강제 호출
//            em.flush();
//
//            tx.commit();
//
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }        

//        // 준영속
//        try {
//            Member member = em.find(Member.class, 1L);
//            member.setUsername("qwer");
//            
//            em.detach(member);
//            // 준영속 상태기 때문에 업데이트가 일어나지 않음
//            tx.commit();
//
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }        

        // 준영속
//        try {
//            Member member = new Member();
//            member.setId("idA");
//            member.setUsername("qwer");
//
//            em.detach(member);
//            // 준영속 상태기 때문에 업데이트가 일어나지 않음
//            tx.commit();
//
//        } catch (Exception e) {
//            tx.rollback();
//        } finally {
//            em.close();
//        }

        emf.close();
    }

}
