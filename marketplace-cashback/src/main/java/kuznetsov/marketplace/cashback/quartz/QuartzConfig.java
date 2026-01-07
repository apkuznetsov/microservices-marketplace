package kuznetsov.marketplace.cashback.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail cashbackPaymentJobDetail() {
        return JobBuilder.newJob(CashbackPaymentJob.class)
                .withIdentity("cashbackPaymentJob")
                .withDescription("Monthly cashback payment job")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger cashbackPaymentTrigger(JobDetail cashbackPaymentJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(cashbackPaymentJobDetail)
                .withIdentity("cashbackPaymentTrigger")
                .withDescription("Trigger for monthly cashback payment")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 2 1 * ?"))
                .build();
    }

}
