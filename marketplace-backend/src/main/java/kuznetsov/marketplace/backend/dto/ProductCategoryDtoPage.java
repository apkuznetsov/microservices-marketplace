package kuznetsov.marketplace.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
