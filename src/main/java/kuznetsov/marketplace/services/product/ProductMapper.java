package kuznetsov.marketplace.services.product;

import kuznetsov.marketplace.models.product.Product;
import kuznetsov.marketplace.models.product.ProductCategory;
import kuznetsov.marketplace.models.user.Seller;
import kuznetsov.marketplace.services.product.dto.ProductCategoryDto;
import kuznetsov.marketplace.services.product.dto.ProductDto;
import kuznetsov.marketplace.services.user.dto.SellerDto;

public interface ProductMapper {

  default Product toProduct(ProductDto productDto) {
    return Product.builder()
        .title(productDto.getTitle())
        .description(productDto.getDescription())
        .techDescription(productDto.getTechDescription())
        .centPrice((long) (productDto.getPrice() * 100.0))
        .build();
  }

  default Product toProduct(ProductDto productDto, ProductCategory category, Seller seller) {
    Product product = this.toProduct(productDto);
    product.setCategory(category);
    product.setSeller(seller);

    return product;
  }

  default ProductDto toProductDto(Product product, ProductCategory category, Seller seller) {
    ProductCategoryDto categoryDto = this.toProductCategoryDto(category);
    SellerDto sellerDto = this.toProductSellerDto(seller);

    return ProductDto.builder()
        .id(product.getId())
        .title(product.getTitle())
        .description(product.getDescription())
        .techDescription(product.getTechDescription())
        .categoryId(categoryDto.getId())
        .categoryName(categoryDto.getName())
        .price(product.getCentPrice() / 100.0)
        .sellerName(sellerDto.getName())
        .sellerAddress(sellerDto.getAddress())
        .sellerCountry(sellerDto.getCountry())
        .sellerEmail(sellerDto.getEmail())
        .imageUrls(null)
        .build();
  }

  private ProductCategoryDto toProductCategoryDto(ProductCategory category) {
    Long categoryId;
    String categoryName;
    if (category != null) {
      categoryId = category.getId();
      categoryName = category.getName();
    } else {
      categoryId = null;
      categoryName = null;
    }

    return ProductCategoryDto.builder()
        .id(categoryId)
        .name(categoryName)
        .build();
  }

  private SellerDto toProductSellerDto(Seller seller) {
    return SellerDto.builder()
        .name(seller.getName())
        .address(seller.getAddress())
        .country(seller.getCountry())
        .email(seller.getPublicEmail())
        .build();
  }

}
