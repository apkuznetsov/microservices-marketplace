package kuznetsov.marketplace.services.user.exception;

import kuznetsov.marketplace.services.exception.ServiceErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ServiceErrorCode {

  USER_NOT_FOUND(
      "User Not Found",
      "The specified user is not found in the system.",
      HttpStatus.NOT_FOUND);

  private final String name;
  private final String message;
  private final HttpStatus httpStatus;

}
