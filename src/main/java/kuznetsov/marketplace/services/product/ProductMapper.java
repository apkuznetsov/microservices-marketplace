package kuznetsov.marketplace.services.product;

import kuznetsov.marketplace.domain.product.Product;
import kuznetsov.marketplace.domain.product.ProductCategory;
import kuznetsov.marketplace.domain.user.Seller;
import kuznetsov.marketplace.services.product.dto.ProductDto;

public interface ProductMapper {

  default Product toProductDto(ProductDto productDto) {
    return Product.builder()
        .id(null)
        .title(productDto.getTitle())
        .description(productDto.getDescription())
        .techDescription(productDto.getTechDescription())
        .category(null)
        .centPrice((long) (productDto.getPrice() * 100.0))
        .seller(null)
        .imageUrls(null)
        .build();
  }

  default Product toProduct(ProductDto productDto, Seller seller, ProductCategory category) {
    Product product = this.toProductDto(productDto);
    product.setSeller(seller);
    product.setCategory(category);

    return product;
  }

}
