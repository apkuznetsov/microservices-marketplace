package kuznetsov.marketplace.server.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ServiceErrorCode {

    PRODUCT_PRICE_OUT_OF_RANGE(
            "Product Price Out of Range",
            "The specified price is less than the minimum or greater than the maximum.",
            HttpStatus.BAD_REQUEST),

    PRODUCT_NOT_FOUND(
            "Product Not Found",
            "The specified product is not found in the system.",
            HttpStatus.NOT_FOUND),

    PRODUCT_NOT_VALID_IMAGE_URL(
            "Product Not Valid Image Url",
            "The specified image url is not valid.",
            HttpStatus.BAD_REQUEST);

    private final String name;
    private final String message;
    private final HttpStatus httpStatus;

}
