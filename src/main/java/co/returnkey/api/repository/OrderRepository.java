package co.returnkey.api.repository;

import co.returnkey.api.entity.OrderEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  Optional<OrderEntity> findOneByOrderIdAndEmailAddress(String orderId, String emailAddress);
}
