package kuznetsov.marketplace.backend.web;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.ConstraintViolationException;
import kuznetsov.marketplace.backend.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
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

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<?> unauthorizedException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception);
    }

    @ExceptionHandler({
            MalformedJwtException.class,
            MismatchedInputException.class,
            BindValidationException.class,
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            MissingPathVariableException.class
    })
    public ResponseEntity<?> argumentException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .badRequest()
                .body(exception);
    }

}

