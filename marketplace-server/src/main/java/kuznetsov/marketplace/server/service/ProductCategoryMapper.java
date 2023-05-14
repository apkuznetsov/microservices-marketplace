package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.domain.ProductCategory;
import kuznetsov.marketplace.server.dto.ProductCategoryDto;
import kuznetsov.marketplace.server.dto.ProductCategoryDtoPage;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface ProductCategoryMapper {

    default ProductCategory toProductCategory(ProductCategoryDto categoryDto) {
        return ProductCategory.builder()
                .name(categoryDto.getName())
                .coverUrl(categoryDto.getCoverUrl())
                .build();
    }

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
                .categories(categoryDtoList)
                .build();
    }

}
