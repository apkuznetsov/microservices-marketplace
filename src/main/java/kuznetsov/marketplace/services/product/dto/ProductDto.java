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

  private String title;

  private String description;

  private String techDescription;

  private Long categoryId;

  private double price;

  private List<String> imageUrls;

}
