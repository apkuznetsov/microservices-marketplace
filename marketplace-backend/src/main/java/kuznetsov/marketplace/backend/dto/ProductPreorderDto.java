package kuznetsov.marketplace.backend.dto;

import kuznetsov.marketplace.backend.domain.PreorderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
