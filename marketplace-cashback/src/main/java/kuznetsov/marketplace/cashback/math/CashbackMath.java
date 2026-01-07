package kuznetsov.marketplace.cashback.math;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CashbackMath {

    private static final int CASHBACK_SCALE = 2;
    private static final RoundingMode CASHBACK_ROUNDING = RoundingMode.HALF_UP;

    @NonNull
    public static BigDecimal calculateCashback(@NonNull BigDecimal purchaseAmount, @NonNull BigDecimal cashbackRate) {
        return purchaseAmount.multiply(cashbackRate)
                .setScale(CASHBACK_SCALE, CASHBACK_ROUNDING);
    }

}
