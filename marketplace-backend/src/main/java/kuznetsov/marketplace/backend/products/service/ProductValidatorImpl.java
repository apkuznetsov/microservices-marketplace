package kuznetsov.marketplace.backend.products.service;

import kuznetsov.marketplace.backend.exception.service.ServiceException;
import kuznetsov.marketplace.backend.products.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.net.URL;

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
            throw new ServiceException(ProductErrorCode.PRODUCT_PRICE_OUT_OF_RANGE);
        }
    }

    private void validateImageUrlsOrThrow(ProductDto productDto) {
        for (String stringImageUrl : productDto.getImageUrls()) {
            if (!isValidUrl(stringImageUrl)) {
                throw new ServiceException(ProductErrorCode.PRODUCT_NOT_VALID_IMAGE_URL);
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
