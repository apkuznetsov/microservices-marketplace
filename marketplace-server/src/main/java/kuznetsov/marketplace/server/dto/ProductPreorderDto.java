package kuznetsov.marketplace.server.dto;

import kuznetsov.marketplace.server.domain.PreorderStatus;
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
