package kuznetsov.marketplace.services.preorder;

import static kuznetsov.marketplace.services.preorder.PreorderErrorCode.PREORDER_NOT_FOUND;

import java.util.List;
import java.util.stream.Collectors;
import kuznetsov.marketplace.models.preorder.PreorderInfo;
import kuznetsov.marketplace.models.preorder.PreorderStatus;
import kuznetsov.marketplace.models.product.Product;
import kuznetsov.marketplace.models.product.ProductCategory;
import kuznetsov.marketplace.models.product.ProductImageUrl;
import kuznetsov.marketplace.models.user.Seller;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.preorder.dto.PreorderDto;
import kuznetsov.marketplace.services.preorder.dto.PreorderDtoPage;
import org.springframework.data.domain.Page;

public interface PreorderMapper {

  default PreorderInfo toPreorderInfo(PreorderDto preorderDto) {
    return PreorderInfo.builder()
        .id(null)
        .centPriceWithoutDiscount((long) (preorderDto.getPriceWithoutDiscount() * 100.0))
        .preorderStatus(PreorderStatus.STARTED)
        .preorderExpectedQuantity(preorderDto.getPreorderExpectedQuantity())
        .preorderEndsAt(preorderDto.getPreorderEndsAt())
        .product(null)
        .build();
  }

  default Product toProduct(PreorderDto preorderDto, Seller seller, ProductCategory category) {
    PreorderInfo preorderInfo = this.toPreorderInfo(preorderDto);

    return Product.builder()
        .title(preorderDto.getTitle())
        .description(preorderDto.getDescription())
        .techDescription(preorderDto.getTechDescription())
        .centPrice((long) (preorderDto.getPrice() * 100.0))
        .seller(seller)
        .category(category)
        .preorderInfo(preorderInfo)
        .build();
  }

  default PreorderDto toPreorderDto(Product product) {
    PreorderInfo preorderInfo = product.getPreorderInfo();
    if (preorderInfo == null) {
      throw new ServiceException(PREORDER_NOT_FOUND);
    }

    ProductCategory category = product.getCategory();
    Seller seller = product.getSeller();
    List<String> stringImageUrls = this.toStringImageUrls(product.getImageUrls());

    return PreorderDto.builder()
        .id(product.getId())
        .title(product.getTitle())
        .description(product.getDescription())
        .techDescription(product.getTechDescription())
        .price(product.getCentPrice() / 100.0)
        .priceWithoutDiscount(preorderInfo.getCentPriceWithoutDiscount() / 100.0)
        .categoryId(category.getId())
        .categoryName(category.getName())
        .imageUrls(stringImageUrls)
        .sellerId(seller.getId())
        .sellerEmail(seller.getPublicEmail())
        .sellerName(seller.getName())
        .sellerAddress(seller.getAddress())
        .preorderStatus(preorderInfo.getPreorderStatus())
        .preorderExpectedQuantity(preorderInfo.getPreorderExpectedQuantity())
        .preorderEndsAt(preorderInfo.getPreorderEndsAt())
        .build();
  }

  PreorderDto toPreorderDto(Product product, String userEmail);

  default List<PreorderDto> toPreorderDtoList(List<Product> products) {
    return products.stream()
        .map(this::toPreorderDto)
        .collect(Collectors.toList());
  }

  List<PreorderDto> toPreorderDtoList(List<Product> products, String userEmail);

  default PreorderDtoPage toPreorderDtoPage(Page<Product> productsPage) {
    List<PreorderDto> preorderDtoList = productsPage.getContent().stream()
        .map(this::toPreorderDto)
        .collect(Collectors.toList());

    return PreorderDtoPage.builder()
        .totalPreorders(productsPage.getTotalElements())
        .totalPreordersPages(productsPage.getTotalPages())
        .preordersMaxPageSize(productsPage.getSize())
        .preordersPageNumber(productsPage.getNumber() + 1)
        .preorders(preorderDtoList)
        .build();
  }

  PreorderDtoPage toPreorderDtoPage(Page<Product> productsPage, String userEmail);

  private List<String> toStringImageUrls(List<ProductImageUrl> productImageUrls) {
    List<String> stringImageUrls;
    if (productImageUrls == null || productImageUrls.size() == 0) {
      stringImageUrls = List.of();
    } else {
      stringImageUrls = productImageUrls.stream()
          .map(ProductImageUrl::getUrl)
          .collect(Collectors.toList());
    }

    return stringImageUrls;
  }

}
