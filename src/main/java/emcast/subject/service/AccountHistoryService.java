package emcast.subject.service;

import emcast.subject.domain.Account;
import emcast.subject.domain.AccountHistory;
import emcast.subject.domain.TransactionStatus;
import emcast.subject.domain.User;
import emcast.subject.dto.service.AccountHistoryInfo;
import emcast.subject.repository.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW) // 내역이 저장이되지 않더라도 진행
    public void saveAccountHistory(String memo, BigDecimal balance, Account account, User user, TransactionStatus status) {
        AccountHistory newHistory = AccountHistory.createHistory(memo, balance, account, user, status);
        accountHistoryRepository.save(newHistory);
    }

    public List<AccountHistoryInfo> getAccountHistoryInfos(Long userId) {
        List<AccountHistoryInfo> accountHistoryInfos = accountHistoryRepository.findAllByUserId(userId).stream()
                .map(ah -> new AccountHistoryInfo(ah.getMemo(), ah.getAmount().toBigInteger(), ah.getStatus(), ah.getTransTime()))
                .toList();

        return accountHistoryInfos;
    }
}
