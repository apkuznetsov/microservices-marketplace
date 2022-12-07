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
public class ProductDtoPage {

  private Long totalProducts;

  private Integer totalProductsPages;

  private Integer productsMaxPageSize;

  private Integer productsPageNumber;

  private List<ProductDto> products;

}
