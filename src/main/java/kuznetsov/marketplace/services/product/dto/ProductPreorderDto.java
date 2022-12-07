package kuznetsov.marketplace.services.product.dto;

import java.time.LocalDateTime;
import kuznetsov.marketplace.models.preorder.PreorderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPreorderDto {

  private Double priceWithoutDiscount;

  private Integer preorderExpectedQuantity;

  private Integer preorderCurrentQuantity;

  private LocalDateTime preorderEndsAt;

  private PreorderStatus preorderStatus;

}
