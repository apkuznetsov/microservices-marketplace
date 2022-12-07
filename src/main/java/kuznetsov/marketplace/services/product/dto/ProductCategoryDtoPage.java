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
public class ProductCategoryDtoPage {

  private Long totalCategories;

  private Integer totalCategoriesPages;

  private Integer categoriesMaxPageSize;

  private Integer categoriesPageNumber;

  private List<ProductCategoryDto> categories;

}
