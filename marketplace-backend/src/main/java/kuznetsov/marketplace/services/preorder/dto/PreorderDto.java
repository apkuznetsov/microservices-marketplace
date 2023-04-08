package kuznetsov.marketplace.services.preorder.dto;

import java.time.LocalDateTime;
import java.util.List;
import kuznetsov.marketplace.models.preorder.PreorderParticipationStatus;
import kuznetsov.marketplace.models.preorder.PreorderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
