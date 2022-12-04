package kuznetsov.marketplace.services.product;

import kuznetsov.marketplace.services.product.dto.ProductCategoryDto;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDtoPage;

public interface ProductCategoryService {

  ProductCategoryDto addCategory(ProductCategoryDto category);

  ProductCategoryDto getCategoryById(long categoryId);

  ProductCategoryDtoPage getPagedCategories(int pageNum);

}
