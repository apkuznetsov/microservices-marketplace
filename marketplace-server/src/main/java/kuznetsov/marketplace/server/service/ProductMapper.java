package kuznetsov.marketplace.server.service;

import kuznetsov.marketplace.server.domain.PreorderInfo;
import kuznetsov.marketplace.server.domain.Product;
import kuznetsov.marketplace.server.domain.ProductCategory;
import kuznetsov.marketplace.server.domain.Seller;
import kuznetsov.marketplace.server.dto.ProductCategoryDto;
import kuznetsov.marketplace.server.dto.ProductDto;
import kuznetsov.marketplace.server.dto.ProductDtoPage;
import kuznetsov.marketplace.server.dto.ProductPreorderDto;
import kuznetsov.marketplace.server.dto.SellerDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

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
        Product product = toProduct(productDto);
        product.setCategory(category);
        product.setSeller(seller);

        return product;
    }

    default ProductDto toProductDto(Product product) {
        return toProductDto(product, product.getCategory(), product.getSeller());
    }

    default ProductDto toProductDto(Product product, ProductCategory category, Seller seller) {
        ProductCategoryDto categoryDto = toProductCategoryDto(category);
        SellerDto sellerDto = toProductSellerDto(seller);
        ProductPreorderDto productPreorderDto = toProductPreorderDto(product);

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
                .preorder(productPreorderDto)
                .imageUrls(null)
                .build();
    }

    default ProductDtoPage toProductDtoPage(Page<Product> productsPage) {
        List<ProductDto> productDtoList = productsPage.getContent().stream()
                .map(this::toProductDto)
                .collect(Collectors.toList());

        return ProductDtoPage.builder()
                .totalProducts(productsPage.getTotalElements())
                .totalProductsPages(productsPage.getTotalPages())
                .productsMaxPageSize(productsPage.getSize())
                .productsPageNumber(productsPage.getNumber() + 1)
                .products(productDtoList)
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

    private ProductPreorderDto toProductPreorderDto(Product product) {
        ProductPreorderDto productPreorderDto;
        PreorderInfo preorderInfo = product.getPreorderInfo();
        if (preorderInfo == null) {
            productPreorderDto = null;
        } else {
            productPreorderDto = ProductPreorderDto.builder()
                    .priceWithoutDiscount(preorderInfo.getCentPriceWithoutDiscount() / 100.0)
                    .preorderStatus(preorderInfo.getPreorderStatus())
                    .preorderExpectedQuantity(preorderInfo.getPreorderExpectedQuantity())
                    .preorderEndsAt(preorderInfo.getPreorderEndsAt())
                    .build();
        }

        return productPreorderDto;
    }

}
