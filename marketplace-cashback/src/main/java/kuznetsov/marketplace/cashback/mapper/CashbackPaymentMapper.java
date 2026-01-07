package kuznetsov.marketplace.cashback.mapper;

import kuznetsov.marketplace.cashback.config.Now;
import kuznetsov.marketplace.cashback.model.Card;
import kuznetsov.marketplace.cashback.model.CashbackPayment;
import kuznetsov.marketplace.cashback.model.PaymentStatus;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.YearMonth;

@Component
public class CashbackPaymentMapper {

    @NonNull
    public CashbackPayment buildCashbackCompleted(@NonNull Card card, @NonNull BigDecimal cashbackAmount, @NonNull YearMonth period) {
        return CashbackPayment.builder()
                .card(card)
                .amount(cashbackAmount)
                .paymentPeriod(period)
                .status(PaymentStatus.COMPLETED)
                .paidAt(Now.localDateTime())
                .build();
    }

    @NonNull
    public CashbackPayment buildCashbackFailed(@NonNull Card card, @NonNull BigDecimal cashbackAmount, @NonNull YearMonth period, String message) {
        return CashbackPayment.builder()
                .card(card)
                .amount(cashbackAmount)
                .paymentPeriod(period)
                .status(PaymentStatus.FAILED)
                .failureReason(message)
                .build();
    }

}
