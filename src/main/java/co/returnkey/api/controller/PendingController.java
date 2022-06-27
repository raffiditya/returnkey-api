package co.returnkey.api.controller;

import co.returnkey.api.domain.TokenRequest;
import co.returnkey.api.domain.TokenResponse;
import co.returnkey.api.service.TokenService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pending")
public class PendingController {

  private final TokenService tokenService;

  @PostMapping("/returns")
  public TokenResponse createReturnToken(@RequestBody @Valid final TokenRequest request) {
    return new TokenResponse()
        .setAccessToken(tokenService.createToken(request));
  }
}
