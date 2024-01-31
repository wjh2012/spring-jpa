package hellojpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Member extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    
    @Column(name="USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;
    
//    @ManyToOne
//    @JoinColumn(name = "TEAM_ID")
//    private Team team;

    // 1:N 양방향
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false) // 읽기전용으로 만듬으로써 비공식적인 방법
    private Team team;

    // 1:1 주 테이블에 외래키
    @OneToOne
    @JoinColumn(name="LOCKER_ID", unique = true)
    private Locker locker;

    // N:N
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();
    
    // 연관관계 편의 메서드
//    public void changeTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }
}