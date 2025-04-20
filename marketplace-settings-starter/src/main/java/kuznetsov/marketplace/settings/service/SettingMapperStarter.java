package kuznetsov.marketplace.settings.service;

import kuznetsov.marketplace.settings.domain.SettingStarter;
import kuznetsov.marketplace.settings.dto.SettingDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SettingMapperStarter {

    SettingDto toDto(SettingStarter entity);

    List<SettingDto> toDtoList(List<SettingStarter> entityList);

}
