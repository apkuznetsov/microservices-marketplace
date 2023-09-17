package kuznetsov.marketplace.backend.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
