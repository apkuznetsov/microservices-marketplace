package kuznetsov.marketplace.core.products.service;

import kuznetsov.marketplace.core.exception.ServiceException;
import kuznetsov.marketplace.core.pagination.PageErrorCode;
import kuznetsov.marketplace.core.products.domain.ProductCategory;
import kuznetsov.marketplace.core.products.dto.ProductCategoryDto;
import kuznetsov.marketplace.core.products.dto.ProductCategoryDtoPage;
import kuznetsov.marketplace.core.products.repos.ProductCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kuznetsov.marketplace.core.products.service.ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryProperties categoryProps;
    private final ProductCategoryMapper categoryMapper;

    private final ProductCategoryRepo categoryRepo;

    @Override
    @Transactional
    public ProductCategoryDto addCategory(ProductCategoryDto categoryDto) {
        ProductCategory category = categoryMapper.toProductCategory(categoryDto);
        ProductCategory savedCategory = categoryRepo.saveAndFlush(category);

        return categoryMapper.toProductCategoryDto(savedCategory);
    }

    @Override
    @Transactional
    public ProductCategoryDto updateCategory(long categoryId, ProductCategoryDto categoryDto) {
        ProductCategory category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ServiceException(PRODUCT_CATEGORY_NOT_FOUND));

        ProductCategory newCategory = categoryMapper.toProductCategory(categoryDto);
        newCategory.setId(category.getId());
        ProductCategory updatedCategory = categoryRepo.saveAndFlush(newCategory);

        return categoryMapper.toProductCategoryDto(updatedCategory);
    }

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
            throw new ServiceException(PageErrorCode.NOT_POSITIVE_PAGE_NUMBER);
        }

        Pageable page = PageRequest.of(pageNum, categoryProps.getPageSize());
        Page<ProductCategory> pagedCategories = categoryRepo.findAll(page);

        return categoryMapper.toProductCategoryDtoPage(pagedCategories);
    }

}
