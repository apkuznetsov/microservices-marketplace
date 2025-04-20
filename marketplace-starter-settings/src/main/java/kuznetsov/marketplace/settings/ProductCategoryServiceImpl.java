package kuznetsov.marketplace.settings;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryMapper categoryMapper;
    private final ProductCategoryRepository categoryRepo;

    @Override
    public Optional<ProductCategoryDto> findById(long categoryId) {
        return categoryRepo
                .findById(categoryId)
                .map(categoryMapper::toDto);
    }

}
