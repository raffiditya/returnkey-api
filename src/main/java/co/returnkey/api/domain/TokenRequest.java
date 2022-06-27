package co.returnkey.api.domain;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenRequest {

  @NotBlank
  private String orderId;

  @NotBlank
  private String emailAddress;
}
