package kuznetsov.marketplace.settings;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategoryDto toDto(ProductCategory entity);

    default List<ProductCategoryDto> toDtoList(List<ProductCategory> categories) {
        return categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
