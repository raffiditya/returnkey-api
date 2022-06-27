package co.returnkey.api.service;

import co.returnkey.api.domain.Item;
import co.returnkey.api.domain.ReturnItemStatus;
import co.returnkey.api.domain.ReturnRequest;
import co.returnkey.api.domain.ReturnStatus;
import co.returnkey.api.entity.ItemEntity;
import co.returnkey.api.entity.OrderEntity;
import co.returnkey.api.entity.OrderItemEntity;
import co.returnkey.api.entity.ReturnEntity;
import co.returnkey.api.entity.ReturnItemEntity;
import co.returnkey.api.entity.ReturnTokenEntity;
import co.returnkey.api.repository.ItemRepository;
import co.returnkey.api.repository.OrderItemRepository;
import co.returnkey.api.repository.ReturnRepository;
import co.returnkey.api.repository.ReturnTokenRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReturnService {

  private final ReturnRepository returnRepository;
  private final ReturnTokenRepository returnTokenRepository;
  private final ItemRepository itemRepository;
  private final OrderItemRepository orderItemRepository;

  public ReturnEntity findById(final Long id) {
    final var returnEntity = returnRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("ID not found."));
    final var displayedReturnItems = returnEntity.getReturnItems()
        .stream()
        .filter(returnItemEntity ->
            !ReturnItemStatus.REJECTED.toString().equals(returnItemEntity.getStatus()))
        .collect(Collectors.toList());
    return returnEntity
        .setReturnItems(displayedReturnItems);
  }

  public ReturnEntity createReturn(final ReturnRequest request) {
    final var order =
        returnTokenRepository.findById(UUID.fromString(request.getAccessToken()))
            .map(ReturnTokenEntity::getOrder)
            .orElseThrow(() -> new IllegalArgumentException("Token not found."));

    final var skus = request.getItems()
        .stream()
        .map(Item::getSku)
        .collect(Collectors.toSet());
    if (!orderItemRepository.existsByOrderAndItem_SkuIn(order, skus)) {
      throw new IllegalArgumentException("Invalid items order");
    }

    final var itemMap = request.getItems()
        .stream()
        .collect(Collectors.toMap(Item::getSku, Function.identity()));
    final var returnEntity = new ReturnEntity()
        .setOrder(order)
        .setStatus(ReturnStatus.AWAITING_APPROVAL.toString());
    final var returnItems = getValidReturnItems(itemMap, order, returnEntity);
    final var refundAmount = returnItems.stream()
        .map(ReturnItemEntity::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    returnEntity
        .setReturnItems(returnItems)
        .setRefundAmount(refundAmount);

    return returnRepository.save(returnEntity);
  }

  private List<ReturnItemEntity> getValidReturnItems(final Map<String, Item> requestItemMap,
      final OrderEntity orderEntity, final ReturnEntity returnEntity) {

    final var availableItemMap = orderEntity.getOrderItems()
        .stream()
        .collect(Collectors.toMap(
            orderItem -> orderItem.getItem().getSku(), OrderItemEntity::getQuantity));

    final var skus = requestItemMap.values()
        .stream()
        .map(Item::getSku)
        .collect(Collectors.toList());
    updateAvailableQuantity(orderEntity, availableItemMap);

    return itemRepository.findBySkuIn(skus)
        .stream()
        .peek(item -> validateQuantity(availableItemMap, requestItemMap, item))
        .map(item -> map(item, requestItemMap.get(item.getSku())))
        .map(item -> item.setReturnEntity(returnEntity))
        .collect(Collectors.toList());
  }

  private void updateAvailableQuantity(final OrderEntity orderEntity,
      final Map<String, Integer> availableItemMap) {

    returnRepository.findByOrder(orderEntity)
        .stream()
        .flatMap(returnEntity -> returnEntity.getReturnItems().stream())
        .filter(returnItemEntity ->
            !ReturnItemStatus.REJECTED.toString().equals(returnItemEntity.getStatus()))
        .forEach(returnItem ->
                availableItemMap.computeIfPresent(
                    returnItem.getItem().getSku(),
                    (sku, available) -> available - returnItem.getQuantity()));
  }

  private void validateQuantity(final Map<String, Integer> availableItemMap,
      final Map<String, Item> requestItemMap, final ItemEntity itemEntity) {

    final var available = availableItemMap.computeIfAbsent(itemEntity.getSku(), key -> 0);
    final var requested = requestItemMap.get(itemEntity.getSku()).getQuantity();
    if (available - requested < 0) {
      throw new IllegalArgumentException("Item " + itemEntity.getItemName() + " not available");
    }
  }

  private ReturnItemEntity map(final ItemEntity itemEntity, final Item requestItem) {
    return new ReturnItemEntity()
        .setItem(itemEntity)
        .setQuantity(requestItem.getQuantity())
        .setTotalPrice(itemEntity.getPrice().multiply(
            BigDecimal.valueOf(requestItem.getQuantity())));
  }
}
