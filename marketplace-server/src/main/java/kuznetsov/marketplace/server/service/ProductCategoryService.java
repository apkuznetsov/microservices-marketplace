package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.dto.ProductCategoryDto;
import kuznetsov.marketplace.server.dto.ProductCategoryDtoPage;

public interface ProductCategoryService {

    ProductCategoryDto addCategory(ProductCategoryDto category);

    ProductCategoryDto updateCategory(long categoryId, ProductCategoryDto categoryDto);

    ProductCategoryDto getCategoryById(long categoryId);

    ProductCategoryDtoPage getPagedCategories(int pageNum);

}
