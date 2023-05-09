package kuznetsov.marketplace.server.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ServiceErrorCode {

    USER_NOT_FOUND(
            "User Not Found",
            "The specified user is not found in the system.",
            HttpStatus.NOT_FOUND),

    USER_ALREADY_EXISTS(
            "User Already Exists",
            "The specified user already exists in the system.",
            HttpStatus.BAD_REQUEST),

    USER_EMAIL_NOT_CONFIRMED(
            "Email Not Confirmed",
            "The email address has not been confirmed.",
            HttpStatus.BAD_REQUEST),

    USER_EMAIL_ALREADY_CONFIRMED(
            "Email Already Confirmed",
            "The email address has already been confirmed.",
            HttpStatus.BAD_REQUEST),

    USER_HAS_NO_PERMISSION(
            "User Has No Permission",
            "Current user has no permissions to do the operation.",
            HttpStatus.FORBIDDEN);

    private final String name;
    private final String message;
    private final HttpStatus httpStatus;

}
