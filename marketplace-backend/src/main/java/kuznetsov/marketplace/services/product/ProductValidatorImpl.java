package kuznetsov.marketplace.services.product;

import static kuznetsov.marketplace.services.product.ProductErrorCode.PRODUCT_NOT_VALID_IMAGE_URL;
import static kuznetsov.marketplace.services.product.ProductErrorCode.PRODUCT_PRICE_OUT_OF_RANGE;

import java.net.URL;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.product.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductValidatorImpl implements ProductValidator {

  @Override
  public void validateOrThrow(ProductProperties productProps, ProductDto productDto) {
    validatePriceOrThrow(productProps, productDto);
    validateImageUrlsOrThrow(productDto);
  }

  private void validatePriceOrThrow(ProductProperties productProps, ProductDto productDto) {
    double productPrice = productDto.getPrice();
    if (productPrice > productProps.getMaxPrice()
        || productPrice < productProps.getMinPrice()) {
      throw new ServiceException(PRODUCT_PRICE_OUT_OF_RANGE);
    }
  }

  private void validateImageUrlsOrThrow(ProductDto productDto) {
    for (String stringImageUrl : productDto.getImageUrls()) {
      if (!isValidUrl(stringImageUrl)) {
        throw new ServiceException(PRODUCT_NOT_VALID_IMAGE_URL);
      }
    }
  }

  private boolean isValidUrl(String stringUrl) {
    try {
      URL url = new URL(stringUrl);
      url.toURI();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
