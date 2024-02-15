package kuznetsov.marketplace.core.roles.sellers.service;

import kuznetsov.marketplace.core.exception.ServiceErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SellerErrorCode implements ServiceErrorCode {

    SELLER_NOT_FOUND(
            "Seller Not Found",
            "The specified seller is not found in the system.",
            HttpStatus.NOT_FOUND);

    private final String name;
    private final String message;
    private final HttpStatus httpStatus;

}
