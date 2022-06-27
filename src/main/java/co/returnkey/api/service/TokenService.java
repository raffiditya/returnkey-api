package co.returnkey.api.service;

import co.returnkey.api.domain.TokenRequest;
import co.returnkey.api.entity.ReturnTokenEntity;
import co.returnkey.api.repository.OrderRepository;
import co.returnkey.api.repository.ReturnTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final ReturnTokenRepository returnTokenRepository;
  private final OrderRepository orderRepository;

  public String createToken(final TokenRequest request) {
    final var order = orderRepository
        .findOneByOrderIdAndEmailAddress(request.getOrderId(), request.getEmailAddress())
        .orElseThrow(() -> new IllegalArgumentException("Order and email address not found."));

    final var returnToken = returnTokenRepository.findOneByOrder(order)
        .orElseGet(() -> returnTokenRepository.save(new ReturnTokenEntity().setOrder(order)));

    return returnToken.getToken()
        .toString();
  }
}
