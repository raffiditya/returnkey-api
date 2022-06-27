package co.returnkey.api.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "order_table", uniqueConstraints = @UniqueConstraint(columnNames = "orderId"))
@Data
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String orderId;

  @Column(nullable = false)
  private String emailAddress;

  @OneToMany(mappedBy = "order")
  @Fetch(FetchMode.SUBSELECT)
  private List<OrderItemEntity> orderItems;
}
