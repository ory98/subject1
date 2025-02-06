package emcast.subject.service;

import emcast.subject.domain.Account;
import emcast.subject.domain.AccountHistory;
import emcast.subject.domain.User;
import emcast.subject.repository.AccountHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW) // 내역이 저장이되지 않더라도 진행 
    public void saveAccountHistory(String memo, BigDecimal balance, Account account, User user) {
        AccountHistory newHistory = AccountHistory.createHistory(memo, balance, account, user);
        accountHistoryRepository.save(newHistory);
    }
}
