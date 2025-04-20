package kuznetsov.marketplace.settings.service;

import kuznetsov.marketplace.settings.domain.SettingStarter;
import kuznetsov.marketplace.settings.dto.SettingDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SettingMapperStarter {

    SettingDto toDto(SettingStarter entity);

    default List<SettingDto> toDtoList(List<SettingStarter> categories) {
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
