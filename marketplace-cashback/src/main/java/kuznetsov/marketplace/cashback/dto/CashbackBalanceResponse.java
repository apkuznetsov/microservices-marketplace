package kuznetsov.marketplace.cashback.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CashbackBalanceResponse(

        Long cardId,

        BigDecimal cashbackBalance

) {
}
