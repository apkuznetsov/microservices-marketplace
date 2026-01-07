package kuznetsov.marketplace.cashback.repository;

import kuznetsov.marketplace.cashback.model.CashbackPayment;
import kuznetsov.marketplace.cashback.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

// TODO: inner join Card
@Repository
public interface CashbackPaymentRepository extends JpaRepository<CashbackPayment, Long> {

    List<CashbackPayment> findByPaymentPeriodAndStatus(YearMonth period, PaymentStatus status);

    Optional<CashbackPayment> findByCardIdAndPaymentPeriod(Long cardId, YearMonth period);

    List<CashbackPayment> findByCardIdOrderByPaymentPeriodDesc(Long cardId);

}
