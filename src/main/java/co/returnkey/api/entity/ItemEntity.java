package co.returnkey.api.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Table(name = "item", uniqueConstraints = @UniqueConstraint(columnNames = "sku"))
@Accessors(chain = true)
public class ItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String sku;

  @Column(nullable = false)
  private String itemName;

  @Column(nullable = false)
  private BigDecimal price;
}
