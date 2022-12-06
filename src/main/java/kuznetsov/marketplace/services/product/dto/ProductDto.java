package kuznetsov.marketplace.services.product.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

  private Long id;

  private String title;

  private String description;

  private String techDescription;

  private Long categoryId;

  private String categoryName;

  private double price;

  private String sellerName;

  private String sellerAddress;

  private String sellerCountry;

  private String sellerEmail;

  private List<String> imageUrls;

}
