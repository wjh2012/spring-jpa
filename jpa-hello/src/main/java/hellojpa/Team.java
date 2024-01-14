package hellojpa;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team {

    @Id @GeneratedValue
    private Long id;
    
    private String name;
    
    // 연관관계의 주인이 아님
    @OneToMany(mappedBy = "team")
    private List<Member> members;
}
