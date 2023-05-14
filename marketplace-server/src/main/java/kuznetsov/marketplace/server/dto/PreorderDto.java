package kuznetsov.marketplace.server.dto;

import kuznetsov.marketplace.server.domain.PreorderParticipationStatus;
import kuznetsov.marketplace.server.domain.PreorderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreorderDto {

    private Long id;

    private String title;

    private String description;

    private String techDescription;

    private Long categoryId;

    private String categoryName;

    private Double price;

    private Double priceWithoutDiscount;

    private Long sellerId;

    private String sellerName;

    private String sellerAddress;

    private String sellerEmail;

    private PreorderStatus preorderStatus;

    private PreorderParticipationStatus currentUserParticipationStatus;

    private Integer preorderExpectedQuantity;

    private Integer preorderCurrentQuantity;

    private LocalDateTime preorderEndsAt;

    private List<String> imageUrls;

}
