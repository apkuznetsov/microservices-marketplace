package kuznetsov.marketplace.core.products.service;

import kuznetsov.marketplace.core.exception.ServiceErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProductCategoryErrorCode implements ServiceErrorCode {

    PRODUCT_CATEGORY_NOT_FOUND(
            "Category Not Found",
            "The specified product category is not found in the system.",
            HttpStatus.NOT_FOUND);

    private final String name;
    private final String message;
    private final HttpStatus httpStatus;

}
