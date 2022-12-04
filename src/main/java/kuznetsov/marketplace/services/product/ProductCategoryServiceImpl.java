package kuznetsov.marketplace.services.product;

import static kuznetsov.marketplace.services.pagination.PageErrorCode.NOT_POSITIVE_PAGE_NUMBER;

import kuznetsov.marketplace.database.product.ProductCategoryRepository;
import kuznetsov.marketplace.domain.product.ProductCategory;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDtoPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

  private final ProductProperties productProps;
  private final ProductCategoryMapper categoryMapper;

  private final ProductCategoryRepository categoryRepo;

  @Override
  public ProductCategoryDtoPage getPagedCategories(int pageNum) {
    --pageNum;
    if (pageNum < 0) {
      throw new ServiceException(NOT_POSITIVE_PAGE_NUMBER);
    }

    Pageable page = PageRequest.of(pageNum, productProps.getPageSize());
    Page<ProductCategory> pagedCategories = categoryRepo.findAll(page);

    return categoryMapper.toProductCategoryDtoPage(pagedCategories);
  }

}
