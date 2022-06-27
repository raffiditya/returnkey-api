package co.returnkey.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
    name = "order_item",
    uniqueConstraints = @UniqueConstraint(name = "SKU_ORDER", columnNames = {"order_id", "item_id"})
)
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "order"})
public class OrderItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private OrderEntity order;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false)
  private ItemEntity item;

  private Integer quantity;
}
