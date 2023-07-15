package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.dto.ProductCategoryDto;
import kuznetsov.marketplace.backend.dto.ProductCategoryDtoPage;

public interface ProductCategoryService {

    ProductCategoryDto addCategory(ProductCategoryDto category);

    ProductCategoryDto updateCategory(long categoryId, ProductCategoryDto categoryDto);

    ProductCategoryDto getCategoryById(long categoryId);

    ProductCategoryDtoPage getPagedCategories(int pageNum);

}
