package kuznetsov.marketplace.services.product;

import static kuznetsov.marketplace.services.pagination.PageErrorCode.NOT_POSITIVE_PAGE_NUMBER;
import static kuznetsov.marketplace.services.product.ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND;

import kuznetsov.marketplace.database.product.ProductCategoryRepository;
import kuznetsov.marketplace.domain.product.ProductCategory;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDto;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDtoPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

  private final ProductCategoryProperties categoryProps;
  private final ProductCategoryMapper categoryMapper;

  private final ProductCategoryRepository categoryRepo;

  @Override
  public ProductCategoryDto getCategoryById(long categoryId) {
    ProductCategory category = categoryRepo.findById(categoryId)
        .orElseThrow(() -> new ServiceException(PRODUCT_CATEGORY_NOT_FOUND));

    return categoryMapper.toProductCategoryDto(category);
  }

  @Override
  public ProductCategoryDtoPage getPagedCategories(int pageNum) {
    --pageNum;
    if (pageNum < 0) {
      throw new ServiceException(NOT_POSITIVE_PAGE_NUMBER);
    }

    Pageable page = PageRequest.of(pageNum, categoryProps.getPageSize());
    Page<ProductCategory> pagedCategories = categoryRepo.findAll(page);

    return categoryMapper.toProductCategoryDtoPage(pagedCategories);
  }

}
