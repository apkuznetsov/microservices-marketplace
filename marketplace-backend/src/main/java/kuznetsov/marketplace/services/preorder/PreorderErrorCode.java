package kuznetsov.marketplace.services.preorder;

import kuznetsov.marketplace.services.exception.ServiceErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PreorderErrorCode implements ServiceErrorCode {

  PREORDER_NOT_FOUND(
      "Preorder Not Found",
      "The specified preorder is not found in the system.",
      HttpStatus.NOT_FOUND),

  PRODUCT_NOT_PREORDERABLE(
      "Product Not Preorderable",
      "The specified product cannot be preordered.",
      HttpStatus.BAD_REQUEST),

  PREORDER_DISCOUNTED_PRICE_LESS_THAN_PRICE(
      "Preorder Discounted Price Less Than Price",
      "The specified price with discount is less than the specified non-discounted price.",
      HttpStatus.BAD_REQUEST),

  PREORDER_NOT_VALID_IMAGE_URL(
      "Preorder Not Valid Image Url",
      "The specified image url is not valid.",
      HttpStatus.BAD_REQUEST),

  PREORDER_PRICE_OUT_OF_RANGE(
      "Preorder Price Out of Range",
      "The specified price is less than the minimum or greater than the maximum.",
      HttpStatus.BAD_REQUEST),

  PREORDER_DURATION_OUT_OF_RANGE(
      "Preorder Duration Out of Range",
      "The specified duration is less than the minimum or greater than the maximum.",
      HttpStatus.BAD_REQUEST),

  PREORDER_EXPECTED_QUANTITY_OUT_OF_RANGE(
      "Preorder Expected Quantity Out of Range",
      "The specified expected quantity is less than the minimum or greater than the maximum.",
      HttpStatus.BAD_REQUEST);

  private final String name;
  private final String message;
  private final HttpStatus httpStatus;

}
