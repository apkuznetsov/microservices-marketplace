package kuznetsov.marketplace.services.preorder;

import static kuznetsov.marketplace.services.preorder.PreorderErrorCode.PREORDER_DURATION_OUT_OF_RANGE;
import static kuznetsov.marketplace.services.preorder.PreorderErrorCode.PREORDER_EXPECTED_QUANTITY_OUT_OF_RANGE;
import static kuznetsov.marketplace.services.preorder.PreorderErrorCode.PREORDER_NOT_VALID_IMAGE_URL;
import static kuznetsov.marketplace.services.preorder.PreorderErrorCode.PREORDER_PRICE_OUT_OF_RANGE;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import kuznetsov.marketplace.services.exception.ServiceException;
import kuznetsov.marketplace.services.preorder.dto.PreorderDto;
import org.springframework.stereotype.Component;

@Component
public class PreorderValidatorImpl implements PreorderValidator {

  @Override
  public void validateOrThrow(PreorderProperties preorderProps, PreorderDto preorderDto) {
    validatePriceOrThrow(preorderProps, preorderDto);
    validatePriceWithoutDiscountOrThrow(preorderProps, preorderDto);
    validatePreorderDurationDaysOrThrow(preorderProps, preorderDto);
    validatePreorderExpectedQuantityOrThrow(preorderProps, preorderDto);
    validateImageUrlsOrThrow(preorderDto);
  }

  private void validatePriceOrThrow(PreorderProperties preorderProps, PreorderDto preorderDto) {
    double preorderPrice = preorderDto.getPrice();
    if (preorderPrice > preorderProps.getMaxPrice()
        || preorderPrice < preorderProps.getMinPrice()) {
      throw new ServiceException(PREORDER_PRICE_OUT_OF_RANGE);
    }
  }

  private void validatePriceWithoutDiscountOrThrow(
      PreorderProperties preorderProps, PreorderDto preorderDto) {

    double preorderPriceWithoutDiscount = preorderDto.getPriceWithoutDiscount();
    if (preorderPriceWithoutDiscount > preorderProps.getMaxPrice()
        || preorderPriceWithoutDiscount < preorderProps.getMinPrice()) {
      throw new ServiceException(PREORDER_PRICE_OUT_OF_RANGE);
    }
    if (preorderPriceWithoutDiscount < preorderDto.getPrice()) {
      throw new ServiceException(PREORDER_PRICE_OUT_OF_RANGE);
    }
  }

  private void validatePreorderDurationDaysOrThrow(
      PreorderProperties preorderProps, PreorderDto preorderDto) {

    LocalDateTime fromNowAt = LocalDateTime.now();
    LocalDateTime toPreorderEndsAt = preorderDto.getPreorderEndsAt();
    LocalDateTime daysUntilDateTime = LocalDateTime.from(fromNowAt);
    long preorderDurationDays = daysUntilDateTime.until(toPreorderEndsAt, ChronoUnit.DAYS);

    if (preorderDurationDays > preorderProps.getMaxPreorderDurationDays()
        || preorderDurationDays < preorderProps.getMinPreorderDurationDays()) {
      throw new ServiceException(PREORDER_DURATION_OUT_OF_RANGE);
    }
  }

  private void validatePreorderExpectedQuantityOrThrow(
      PreorderProperties preorderProps, PreorderDto preorderDto) {

    int preorderExpectedQuantity = preorderDto.getPreorderExpectedQuantity();
    if (preorderExpectedQuantity > preorderProps.getMaxPreorderExpectedQuantity()
        || preorderExpectedQuantity < preorderProps.getMinPreorderExpectedQuantity()) {
      throw new ServiceException(PREORDER_EXPECTED_QUANTITY_OUT_OF_RANGE);
    }
  }

  private void validateImageUrlsOrThrow(PreorderDto preorderDto) {
    for (String stringImageUrl : preorderDto.getImageUrls()) {
      if (!isValidUrl(stringImageUrl)) {
        throw new ServiceException(PREORDER_NOT_VALID_IMAGE_URL);
      }
    }
  }

  private boolean isValidUrl(String stringUrl) {
    try {
      URL url = new URL(stringUrl);
      url.toURI();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
