package kuznetsov.marketplace.services.product;

import java.util.List;
import java.util.stream.Collectors;
import kuznetsov.marketplace.domain.product.ProductCategory;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDto;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDtoPage;
import org.springframework.data.domain.Page;

public interface ProductCategoryMapper {

  default ProductCategoryDto toProductCategoryDto(ProductCategory category) {
    return ProductCategoryDto.builder()
        .id(category.getId())
        .name(category.getName())
        .coverUrl(category.getCoverUrl())
        .build();
  }

  default List<ProductCategoryDto> toProductCategoryDtoList(List<ProductCategory> categories) {
    return categories.stream()
        .map(this::toProductCategoryDto)
        .collect(Collectors.toList());
  }

  default ProductCategoryDtoPage toProductCategoryDtoPage(Page<ProductCategory> categoryPage) {
    List<ProductCategoryDto> categoryDtoList = toProductCategoryDtoList(categoryPage.getContent());

    return ProductCategoryDtoPage.builder()
        .totalCategories(categoryPage.getTotalElements())
        .totalCategoriesPages(categoryPage.getTotalPages())
        .categoriesMaxPageSize(categoryPage.getSize())
        .categoriesPageNumber(categoryPage.getNumber() + 1)
        .content(categoryDtoList)
        .build();
  }

}
