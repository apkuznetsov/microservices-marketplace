package kuznetsov.marketplace.settings.service;


import kuznetsov.marketplace.settings.dto.ProductCategoryDto;

import java.util.Optional;

public interface StarterSettingService {

    Optional<ProductCategoryDto> findById(long categoryId);

}
