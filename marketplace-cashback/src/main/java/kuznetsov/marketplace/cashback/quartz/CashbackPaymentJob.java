package kuznetsov.marketplace.cashback.quartz;

import kuznetsov.marketplace.cashback.service.CashbackService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class CashbackPaymentJob implements Job {

    @Autowired
    private CashbackService cashbackService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Executing scheduled cashback payment job");
        try {
            cashbackService.payCashbackForPreviousMonth();
            log.info("Cashback payment job completed successfully");
        } catch (Exception e) {
            log.error("Error executing cashback payment job", e);
            throw new JobExecutionException(e);
        }
    }

}
