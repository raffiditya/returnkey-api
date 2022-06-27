package co.returnkey.api.repository;

import co.returnkey.api.entity.OrderEntity;
import co.returnkey.api.entity.ReturnEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnRepository extends JpaRepository<ReturnEntity, Long> {

  List<ReturnEntity> findByOrder(OrderEntity order);
}
