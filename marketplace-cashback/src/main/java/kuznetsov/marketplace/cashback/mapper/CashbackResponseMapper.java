package kuznetsov.marketplace.cashback.mapper;

import kuznetsov.marketplace.cashback.dto.CashbackPaymentResponse;
import kuznetsov.marketplace.cashback.dto.TransactionResponse;
import kuznetsov.marketplace.cashback.model.CashbackPayment;
import kuznetsov.marketplace.cashback.model.Transaction;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CashbackResponseMapper {

    // TODO: move to the response builder
    private static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    @NonNull
    public TransactionResponse toResponse(@NonNull Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .cardId(transaction.getCard().getId())
                .amount(transaction.getAmount())
                .cashbackAmount(transaction.getCashbackAmount())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .createdAt(transaction.getCreatedAt())
                .processedAt(transaction.getProcessedAt())
                .build();
    }

    @NonNull
    public List<CashbackPaymentResponse> toResponses(@NonNull List<CashbackPayment> payments) {
        return payments.stream()
                .map(this::toResponse)
                .toList();
    }

    @NonNull
    public CashbackPaymentResponse toResponse(@NonNull CashbackPayment payment) {
        return CashbackPaymentResponse.builder()
                .id(payment.getId())
                .cardId(payment.getCard().getId())
                .cardNumber(maskCardNumber(payment.getCard().getCardNumber()))
                .amount(payment.getAmount())
                .paymentPeriod(payment.getPaymentPeriod())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .paidAt(payment.getPaidAt())
                .failureReason(payment.getFailureReason())
                .build();
    }

}
