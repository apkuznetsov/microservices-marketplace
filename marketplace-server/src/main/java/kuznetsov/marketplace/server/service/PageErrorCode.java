package kuznetsov.marketplace.server.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PageErrorCode implements ServiceErrorCode {

    NOT_POSITIVE_PAGE_NUMBER(
            "Not Positive Page Number",
            "The specified page number must be greater than zero.",
            HttpStatus.BAD_REQUEST);

    private final String name;
    private final String message;
    private final HttpStatus httpStatus;

}
