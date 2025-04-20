package kuznetsov.marketplace.settings.service;


import kuznetsov.marketplace.settings.dto.SettingDto;

import java.util.List;

public interface SettingServiceStarter {

    List<SettingDto> findAll();

}
