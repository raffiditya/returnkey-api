package co.returnkey.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "return")
@Data
@Accessors(chain = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "order"})
public class ReturnEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private OrderEntity order;

  @Column(nullable = false)
  private String status;

  @OneToMany(mappedBy = "returnEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @Fetch(FetchMode.JOIN)
  private List<ReturnItemEntity> returnItems;

  @Column(nullable = false)
  private BigDecimal refundAmount;
}
