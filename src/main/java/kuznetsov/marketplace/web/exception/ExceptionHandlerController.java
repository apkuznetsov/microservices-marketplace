package kuznetsov.marketplace.web.exception;

import kuznetsov.marketplace.services.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

  @ExceptionHandler(value = ServiceException.class)
  public ResponseEntity<ServiceException> responseServiceException(
      ServiceException serviceException) {

    log.error(serviceException.getMessage(), serviceException);
    return ResponseEntity
        .status(serviceException.getStatus())
        .body(serviceException);
  }

}
