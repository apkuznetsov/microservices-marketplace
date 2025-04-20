package kuznetsov.marketplace.settings.service;


import kuznetsov.marketplace.settings.domain.SettingStarter;
import kuznetsov.marketplace.settings.dto.SettingDto;
import kuznetsov.marketplace.settings.repo.SettingRepoStarter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SettingServiceStarterImpl implements SettingServiceStarter {

    private final SettingMapperStarter settingMapper;
    private final SettingRepoStarter settingRepo;

    @Override
    public List<SettingDto> findAll() {
        List<SettingStarter> foundSettings = settingRepo.findAll();
        return settingMapper.toDtoList(foundSettings);
    }

}
