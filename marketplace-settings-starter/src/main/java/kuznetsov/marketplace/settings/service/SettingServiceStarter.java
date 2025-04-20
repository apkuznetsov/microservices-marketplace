package kuznetsov.marketplace.settings.service;


import kuznetsov.marketplace.settings.dto.SettingDto;

import java.util.Optional;

public interface SettingServiceStarter {

    Optional<SettingDto> findById(long categoryId);

}
