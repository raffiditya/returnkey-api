package co.returnkey.api.repository;

import co.returnkey.api.entity.OrderEntity;
import co.returnkey.api.entity.ReturnTokenEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnTokenRepository extends JpaRepository<ReturnTokenEntity, UUID> {

  Optional<ReturnTokenEntity> findOneByOrder(final OrderEntity orderEntity);
}
