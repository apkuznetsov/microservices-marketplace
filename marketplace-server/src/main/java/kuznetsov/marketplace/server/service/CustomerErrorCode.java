package kuznetsov.marketplace.server.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomerErrorCode implements ServiceErrorCode {

    CUSTOMER_NOT_FOUND(
            "Customer Not Found",
            "The specified customer is not found in the system.",
            HttpStatus.NOT_FOUND);

    private final String name;
    private final String message;
    private final HttpStatus httpStatus;

}
