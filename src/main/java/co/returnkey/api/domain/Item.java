package co.returnkey.api.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {

  @NotBlank
  private String sku;

  @Positive
  private Integer quantity;

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Item item = (Item) o;

    return sku.equals(item.sku);
  }

  @Override
  public int hashCode() {

    return sku.hashCode();
  }
}
