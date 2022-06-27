package co.returnkey.api.domain;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QcSetStatusRequest {

  @NotNull
  private ReturnItemStatus status;
}
