package kuznetsov.marketplace.services.auth.exception;

import kuznetsov.marketplace.services.exception.ServiceErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ServiceErrorCode {

  AUTH_ERROR(
      "Auth Error",
      "Auth error.",
      HttpStatus.BAD_REQUEST);

  private final String name;
  private final String message;
  private final HttpStatus httpStatus;

}
