package kuznetsov.marketplace.cashback.controller;

import kuznetsov.marketplace.cashback.dto.CashbackBalanceResponse;
import kuznetsov.marketplace.cashback.dto.CashbackPaymentResponse;
import kuznetsov.marketplace.cashback.dto.TransactionResponse;
import kuznetsov.marketplace.cashback.service.CashbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cashback")
@RequiredArgsConstructor
public class CashbackController {

    private final CashbackService cashbackService;

    @GetMapping("/balance/{cardId}")
    @ResponseStatus(OK)
    public CashbackBalanceResponse findCashbackBalance(@PathVariable Long cardId) {
        return cashbackService.findCashbackBalance(cardId);
    }

    @GetMapping("/history/{cardId}")
    @ResponseStatus(OK)
    public List<CashbackPaymentResponse> findCashbackHistory(@PathVariable Long cardId) {
        return cashbackService.findCashbackHistory(cardId);
    }

    @PostMapping("/accrue")
    @ResponseStatus(OK)
    public TransactionResponse accrueCashback(@RequestParam Long cardId, @RequestParam BigDecimal amount) {
        return cashbackService.accrueCashback(cardId, amount);
    }

    @PostMapping("/pay/{cardId}")
    @ResponseStatus(OK)
    public CashbackPaymentResponse payCashback(
            @PathVariable Long cardId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth period
    ) {
        return cashbackService.payCashback(period, cardId);
    }

    @PostMapping("/process-monthly")
    @ResponseStatus(OK)
    public Map<Boolean, Integer> payCashbackForPreviousMonth() {
        return cashbackService.payCashbackForPreviousMonth();
    }

    @PostMapping("/retry-failed")
    @ResponseStatus(OK)
    public Map<Boolean, Integer> retryFailedPayments(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth period
    ) {
        return cashbackService.retryFailedPayments(period);
    }

}
