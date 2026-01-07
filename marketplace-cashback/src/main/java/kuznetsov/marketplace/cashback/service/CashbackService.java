package kuznetsov.marketplace.cashback.service;

import kuznetsov.marketplace.cashback.config.Now;
import kuznetsov.marketplace.cashback.dao.CashbackDao;
import kuznetsov.marketplace.cashback.dto.CashbackBalanceResponse;
import kuznetsov.marketplace.cashback.dto.CashbackPaymentResponse;
import kuznetsov.marketplace.cashback.dto.TransactionResponse;
import kuznetsov.marketplace.cashback.mapper.CashbackResponseMapper;
import kuznetsov.marketplace.cashback.model.Card;
import kuznetsov.marketplace.cashback.model.CashbackPayment;
import kuznetsov.marketplace.cashback.model.PaymentStatus;
import kuznetsov.marketplace.cashback.model.Transaction;
import kuznetsov.marketplace.cashback.repository.CardRepository;
import kuznetsov.marketplace.cashback.repository.CashbackPaymentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashbackService {

    private final CashbackResponseMapper responseMapper;

    // TODO: move to dao layer
    private final CardRepository cardRepo;
    private final CashbackPaymentRepository paymentRepo;

    private final CashbackDao cashbackDao;

    @NonNull
    public CashbackBalanceResponse findCashbackBalance(@NonNull Long cardId) {
        BigDecimal balance = cashbackDao.findCashbackBalance(cardId);
        return CashbackBalanceResponse.builder()
                .cardId(cardId)
                .cashbackBalance(balance)
                .build();
    }

    @NonNull
    public List<CashbackPaymentResponse> findCashbackHistory(@NonNull Long cardId) {
        return cashbackDao.findCashbackHistory(cardId)
                .stream()
                .map(responseMapper::toResponse)
                .toList();
    }

    @NonNull
    public TransactionResponse accrueCashback(@NonNull Long cardId, @NonNull BigDecimal purchaseAmount) {
        Transaction transaction = cashbackDao.accrueCashback(cardId, purchaseAmount);
        return responseMapper.toResponse(transaction);
    }

    @NonNull
    public CashbackPaymentResponse payCashback(@NonNull YearMonth period, @NonNull Long cardId) {
        CashbackPayment payment = cashbackDao.payCashback(period, cardId);
        return responseMapper.toResponse(payment);
    }

    @NonNull
    public Map<Boolean, Integer> payCashbackForPreviousMonth() {
        YearMonth previousMonth = Now.yearMonth().minusMonths(1);
        return payCashbackForAll(previousMonth);
    }

    @NonNull
    public Map<Boolean, Integer> retryFailedPayments(@NonNull YearMonth period) {
        List<CashbackPayment> failedPayments = paymentRepo.findByPaymentPeriodAndStatus(period, PaymentStatus.FAILED);
        Map<Boolean, Integer> successOrFailure = new HashMap<>();
        successOrFailure.put(Boolean.TRUE, 0);
        successOrFailure.put(Boolean.FALSE, 0);

        for (CashbackPayment failedPayment : failedPayments) {
            try {
                cashbackDao.retryCashback(failedPayment);
                successOrFailure.computeIfPresent(Boolean.TRUE, (k, v) -> v + 1);
            } catch (Exception e) {
                log.error("Failed to retry payment for card {}", failedPayment.getCard().getId(), e);
                successOrFailure.computeIfPresent(Boolean.FALSE, (k, v) -> v + 1);
            }
        }

        return successOrFailure;
    }

    @NonNull
    private Map<Boolean, Integer> payCashbackForAll(@NonNull YearMonth period) {
        List<Card> eligibleCards = cardRepo.findActiveCards();
        Map<Boolean, Integer> successOrFailure = new HashMap<>();
        successOrFailure.put(Boolean.TRUE, 0);
        successOrFailure.put(Boolean.FALSE, 0);

        for (Card card : eligibleCards) {
            try {
                cashbackDao.payCashback(period, card);
                successOrFailure.computeIfPresent(Boolean.TRUE, (k, v) -> v + 1);
            } catch (Exception e) {
                log.error("Error processing payment for card {}", card.getId(), e);
                successOrFailure.computeIfPresent(Boolean.FALSE, (k, v) -> v + 1);
            }
        }

        return successOrFailure;
    }

}
