package kuznetsov.marketplace.core.products.service;

import kuznetsov.marketplace.core.products.dto.ProductCategoryDto;
import kuznetsov.marketplace.core.products.dto.ProductCategoryDtoPage;

public interface ProductCategoryService {

    ProductCategoryDto addCategory(ProductCategoryDto category);

    ProductCategoryDto updateCategory(long categoryId, ProductCategoryDto categoryDto);

    ProductCategoryDto getCategoryById(long categoryId);

    ProductCategoryDtoPage getPagedCategories(int pageNum);

}
