package kuznetsov.marketplace.settings.service;


import kuznetsov.marketplace.settings.dto.ProductCategoryDto;
import kuznetsov.marketplace.settings.repos.StarterSettingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StarterSettingServiceImpl implements StarterSettingService {

    private final StarterSettingMapper categoryMapper;
    private final StarterSettingRepo categoryRepo;

    @Override
    public Optional<ProductCategoryDto> findById(long categoryId) {
        return categoryRepo
                .findById(categoryId)
                .map(categoryMapper::toDto);
    }

}
