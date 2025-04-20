package kuznetsov.marketplace.settings;


import java.util.Optional;

public interface ProductCategoryService {

    Optional<ProductCategoryDto> findById(long categoryId);

}
