package co.returnkey.api.repository;

import co.returnkey.api.entity.OrderEntity;
import co.returnkey.api.entity.OrderItemEntity;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

  boolean existsByOrderAndItem_SkuIn(OrderEntity orderEntity, Set<String> skus);
}
