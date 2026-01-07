package kuznetsov.marketplace.cashback.dto;

import kuznetsov.marketplace.cashback.model.TransactionStatus;
import kuznetsov.marketplace.cashback.model.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(

        Long id,

        Long cardId,

        BigDecimal amount,

        BigDecimal cashbackAmount,

        TransactionType type,

        String description,

        TransactionStatus status,

        LocalDateTime createdAt,

        LocalDateTime processedAt

) {
}
