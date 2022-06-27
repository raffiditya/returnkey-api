package co.returnkey.api.repository;

import co.returnkey.api.entity.ItemEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

  List<ItemEntity> findBySkuIn(List<String> skus);
}
