package co.returnkey.api.domain;

import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ReturnRequest {

  @NotBlank
  private String accessToken;

  @NotEmpty
  @Valid
  Set<Item> items;
}
