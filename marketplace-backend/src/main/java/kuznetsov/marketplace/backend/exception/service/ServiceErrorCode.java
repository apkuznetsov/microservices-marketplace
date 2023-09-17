package kuznetsov.marketplace.backend.exception.service;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public interface ServiceErrorCode extends Serializable {

    String getName();

    String getMessage();

    default HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
