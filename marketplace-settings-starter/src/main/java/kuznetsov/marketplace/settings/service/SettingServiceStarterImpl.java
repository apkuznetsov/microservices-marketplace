package kuznetsov.marketplace.settings.service;


import kuznetsov.marketplace.settings.dto.SettingDto;
import kuznetsov.marketplace.settings.repo.SettingRepoStarter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SettingServiceStarterImpl implements SettingServiceStarter {

    private final SettingMapperStarter categoryMapper;
    private final SettingRepoStarter categoryRepo;

    @Override
    public Optional<SettingDto> findById(long categoryId) {
        return categoryRepo
                .findById(categoryId)
                .map(categoryMapper::toDto);
    }

}
