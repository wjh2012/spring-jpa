package hellojpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    
    private String name;
    
    // 연관관계의 주인이 아님
    // 읽기 전용
//    @OneToMany(mappedBy = "team")
//    private List<Member> members = new ArrayList<>();
    // 1:N 단방향
    @OneToMany
    @JoinColumn(name="TEAM_ID")
    private List<Member> members = new ArrayList<>();

//    public void addMember(Member member) {
//        member.setTeam(this);
//        members.add(member);
//    }
}
