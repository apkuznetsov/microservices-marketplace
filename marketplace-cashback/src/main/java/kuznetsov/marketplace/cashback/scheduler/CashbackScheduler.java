package kuznetsov.marketplace.cashback.scheduler;

import kuznetsov.marketplace.cashback.config.Now;
import kuznetsov.marketplace.cashback.service.CashbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CashbackScheduler {

    private final CashbackService cashbackService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void dailyCheck() {
        // TODO: daily check
        if (Now.localDateTime().getDayOfMonth() == 1) {
            processMonthlyPayments();
        }
    }

    private void processMonthlyPayments() {
        log.info("Starting scheduled monthly cashback payment");
        try {
            var payments = cashbackService.payCashbackForPreviousMonth();
            log.info("Successfully processed {} cashback payments", payments.size());
        } catch (Exception e) {
            log.error("Error during monthly cashback payment processing", e);
        }
    }

}
