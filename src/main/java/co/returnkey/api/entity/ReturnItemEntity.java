package co.returnkey.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "return_item")
@Data
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "returnEntity"})
public class ReturnItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "return_id", nullable = false)
  private ReturnEntity returnEntity;

  @ManyToOne(fetch = FetchType.EAGER)
  private ItemEntity item;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private BigDecimal totalPrice;

  private String status;
}
