package jpabook.jpacore.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ORDERS")// DB에 ORDER가 예약어로 걸려있는 경우가 있다
@Getter
@Setter
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long id;
    
    @Column(name = "MEMBER_ID")
    private Long MemberId;
    
    private LocalDateTime orderDate;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
