package kuznetsov.marketplace.cashback.mapper;

import kuznetsov.marketplace.cashback.config.Now;
import kuznetsov.marketplace.cashback.model.Card;
import kuznetsov.marketplace.cashback.model.Transaction;
import kuznetsov.marketplace.cashback.model.TransactionStatus;
import kuznetsov.marketplace.cashback.model.TransactionType;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CashbackTransactionMapper {

    @NonNull
    public Transaction buildCashbackAccrual(@NonNull Card card, @NonNull BigDecimal purchaseAmount, @NonNull BigDecimal cashbackAmount) {
        return Transaction.builder()
                .card(card)
                .amount(purchaseAmount)
                .cashbackAmount(cashbackAmount)
                .type(TransactionType.CASHBACK_ACCRUAL)
                .status(TransactionStatus.COMPLETED)
                .description("Cashback for purchase")
                .processedAt(Now.localDateTime())
                .build();
    }

    @NonNull
    public Transaction buildCashbackPayment(@NonNull Card card, @NonNull BigDecimal cashbackAmount) {
        return Transaction.builder()
                .card(card)
                .amount(cashbackAmount)
                .type(TransactionType.CASHBACK_PAYMENT)
                .description("Monthly cashback payment")
                .status(TransactionStatus.COMPLETED)
                .processedAt(Now.localDateTime())
                .build();
    }

}
