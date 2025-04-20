package kuznetsov.marketplace.settings.service;

import kuznetsov.marketplace.settings.domain.ProductCategory;
import kuznetsov.marketplace.settings.dto.ProductCategoryDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StarterSettingMapper {

    ProductCategoryDto toDto(ProductCategory entity);

    default List<ProductCategoryDto> toDtoList(List<ProductCategory> categories) {
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
