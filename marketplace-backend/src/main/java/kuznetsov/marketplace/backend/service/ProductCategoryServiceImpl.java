package kuznetsov.marketplace.backend.service;

import kuznetsov.marketplace.backend.domain.ProductCategory;
import kuznetsov.marketplace.backend.dto.ProductCategoryDto;
import kuznetsov.marketplace.backend.dto.ProductCategoryDtoPage;
import kuznetsov.marketplace.backend.exception.ServiceException;
import kuznetsov.marketplace.backend.pagination.PageErrorCode;
import kuznetsov.marketplace.backend.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kuznetsov.marketplace.backend.service.ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryProperties categoryProps;
    private final ProductCategoryMapper categoryMapper;

    private final ProductCategoryRepository categoryRepo;

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