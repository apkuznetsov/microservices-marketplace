package kuznetsov.marketplace.cashback.dto;

import kuznetsov.marketplace.cashback.model.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Builder
public record CashbackPaymentResponse(

        Long id,

        Long cardId,

        String cardNumber,

        BigDecimal amount,

        YearMonth paymentPeriod,

        PaymentStatus status,

        LocalDateTime createdAt,

        LocalDateTime paidAt,

        String failureReason

) {
}
