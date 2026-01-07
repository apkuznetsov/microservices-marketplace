package kuznetsov.marketplace.cashback.dao;

import kuznetsov.marketplace.cashback.config.Now;
import kuznetsov.marketplace.cashback.mapper.CashbackPaymentMapper;
import kuznetsov.marketplace.cashback.mapper.CashbackTransactionMapper;
import kuznetsov.marketplace.cashback.math.CashbackMath;
import kuznetsov.marketplace.cashback.model.Card;
import kuznetsov.marketplace.cashback.model.CardStatus;
import kuznetsov.marketplace.cashback.model.CashbackPayment;
import kuznetsov.marketplace.cashback.model.Transaction;
import kuznetsov.marketplace.cashback.repository.CardRepository;
import kuznetsov.marketplace.cashback.repository.CashbackPaymentRepository;
import kuznetsov.marketplace.cashback.repository.TransactionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashbackDao {

    private final CardRepository cardRepo;

    private final CashbackTransactionMapper transactionMapper;
    private final TransactionRepository transactionRepo;

    private final CashbackPaymentMapper paymentMapper;
    private final CashbackPaymentRepository paymentRepo;

    @NonNull
    public static RuntimeException cardNotFound() {
        return new RuntimeException("Card not found");
    }

    @NonNull
    public static RuntimeException cardNotActive() {
        return new RuntimeException("Card is not active");
    }

    @NonNull
    public static RuntimeException cashbackAlreadyPaid() {
        return new RuntimeException("Payment already processed");
    }

    @NonNull
    public static RuntimeException noCashbackToPay() {
        return new RuntimeException("No cashback to pay");
    }

    @Transactional(readOnly = true)
    public BigDecimal findCashbackBalance(Long cardId) {
        Card card = cardRepo.findById(cardId).orElseThrow(CashbackDao::cardNotFound);
        return card.getCashbackBalance();
    }

    @Transactional(readOnly = true)
    public List<CashbackPayment> findCashbackHistory(@NonNull Long cardId) {
        return paymentRepo.findByCardIdOrderByPaymentPeriodDesc(cardId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @NonNull
    public Transaction accrueCashback(@NonNull Long cardId, @NonNull BigDecimal purchaseAmount) {
        Card card = cardRepo.findById(cardId).orElseThrow(CashbackDao::cardNotFound);
        if (!CardStatus.ACTIVE.equals(card.getStatus())) {
            throw cardNotActive();
        }

        BigDecimal cashbackAmount = CashbackMath.calculateCashback(purchaseAmount, card.getCashbackRate());
        card.setCashbackBalance(card.getCashbackBalance().add(cashbackAmount));

        Transaction transaction = transactionMapper.buildCashbackAccrual(card, purchaseAmount, cashbackAmount);
        return transactionRepo.save(transaction);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @NonNull
    public CashbackPayment payCashback(@NonNull YearMonth period, @NonNull Long cardId) {
        Card card = cardRepo.findById(cardId).orElseThrow(CashbackDao::cardNotFound);
        return payCashback(period, card);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @NonNull
    public CashbackPayment payCashback(@NonNull YearMonth period, @NonNull Card card) {
        if (paymentRepo.findByCardIdAndPaymentPeriod(card.getId(), period).isPresent()) {
            throw cashbackAlreadyPaid();
        }

        BigDecimal cashbackAmount = card.getCashbackBalance();
        if (cashbackAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw noCashbackToPay();
        }

        card.setBalance(card.getBalance().add(cashbackAmount));
        card.setCashbackBalance(BigDecimal.ZERO);
        card.setLastCashbackPaymentDate(Now.localDateTime());

        Transaction transaction = transactionMapper.buildCashbackPayment(card, cashbackAmount);
        CashbackPayment completed = paymentMapper.buildCashbackCompleted(card, cashbackAmount, period);
        transactionRepo.save(transaction);
        return paymentRepo.save(completed);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void retryCashback(@NonNull CashbackPayment failedPayment) {
        Card card = failedPayment.getCard();
        if (card.getCashbackBalance().compareTo(BigDecimal.ZERO) <= 0) {
            throw noCashbackToPay();
        }

        YearMonth period = failedPayment.getPaymentPeriod();
        paymentRepo.delete(failedPayment);
        payCashback(period, card);
    }

}
