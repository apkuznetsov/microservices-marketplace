package kuznetsov.marketplace.services.product;

import kuznetsov.marketplace.services.product.dto.ProductCategoryDtoPage;

public interface ProductCategoryService {

  ProductCategoryDtoPage getPagedCategories(int pageNum);

}
