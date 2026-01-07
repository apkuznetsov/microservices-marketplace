package kuznetsov.marketplace.cashback.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Now {

    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Moscow");

    @NonNull
    public static LocalDateTime localDateTime() {
        return LocalDateTime.now(ZONE_ID);
    }

    @NonNull
    public static YearMonth yearMonth() {
        return YearMonth.now(ZONE_ID);
    }

}
