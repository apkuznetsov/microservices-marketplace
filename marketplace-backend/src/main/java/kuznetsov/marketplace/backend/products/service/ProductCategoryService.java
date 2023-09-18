package kuznetsov.marketplace.backend.products.service;

import kuznetsov.marketplace.backend.products.dto.ProductCategoryDto;
import kuznetsov.marketplace.backend.products.dto.ProductCategoryDtoPage;

public interface ProductCategoryService {

    ProductCategoryDto addCategory(ProductCategoryDto category);

    ProductCategoryDto updateCategory(long categoryId, ProductCategoryDto categoryDto);

    ProductCategoryDto getCategoryById(long categoryId);

    ProductCategoryDtoPage getPagedCategories(int pageNum);

}
