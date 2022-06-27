package co.returnkey.api.service;

import co.returnkey.api.domain.ReturnItemStatus;
import co.returnkey.api.domain.ReturnStatus;
import co.returnkey.api.repository.ReturnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class QcService {

  private final ReturnRepository returnRepository;

  public void changeStatus(final Long returnId, final Long itemId,
      final ReturnItemStatus itemStatus) {

    final var returnEntity = returnRepository.findById(returnId)
        .orElseThrow(() -> new IllegalArgumentException("returnId not found."));

    final var returnItems = returnEntity.getReturnItems();
    final var returnItemEntity = returnItems.stream()
        .filter(returnItem -> !StringUtils.hasText(returnItem.getStatus()))
        .filter(returnItem -> returnItem.getItem().getId().equals(itemId))
        .map(returnItem -> returnItem.setStatus(itemStatus.toString()))
        .findAny()
        .orElseThrow(() ->
            new IllegalArgumentException("itemId not found / already quality controlled"));

    if (itemStatus == ReturnItemStatus.REJECTED) {
      final var newRefundAmount = returnEntity.getRefundAmount()
          .subtract(returnItemEntity.getTotalPrice());
      returnEntity.setRefundAmount(newRefundAmount);
    }

    final var allCompleted = returnItems.stream()
        .allMatch(returnItem -> StringUtils.hasLength(returnItem.getStatus()));
    returnEntity.setStatus(allCompleted
        ? ReturnStatus.COMPLETE.toString()
        : ReturnStatus.AWAITING_APPROVAL.toString());

    returnRepository.save(returnEntity);
  }
}
