package emcast.subject.service;

import emcast.subject.domain.*;
import emcast.subject.dto.service.*;
import emcast.subject.exception.CommonException;
import emcast.subject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserService userService;
    private final SavingsService savingsService;
    private final AccountHistoryService accountHistoryService;

    private final AccountRepository accountRepository;

    @Transactional
    public TransactionResponse processTransaction(TransactionDto dto, TransactionStatus transactionStatus) {
        // User 정보 가져오기
        User user = userService.getUserInfo(dto.getUserName());

        // 해당 계좌가 있는지 확인
        Account foundAccount = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, "해당하는 계좌가 없습니다."));

        // 계좌 상태 및 적금 검증
        validateAccount(foundAccount, dto.getBalance(), transactionStatus);

        // 해당 계좌 거래
        if (transactionStatus == TransactionStatus.DEPOSIT) {
            foundAccount.increaseBalance(dto.getBalance());
        } else if (transactionStatus == TransactionStatus.WITHDRAWAL) {
            foundAccount.decreaseBalance(dto.getBalance());
        }

        // 계좌 내역 저장 accountHistory
        accountHistoryService.saveAccountHistory(dto.getMemo(), dto.getBalance(), foundAccount, user, TransactionStatus.DEPOSIT);

        // 반환값
        TransactionResponse response = new TransactionResponse(user.getName(),
                foundAccount.getBankName(), foundAccount.getAccountNumber(), TransactionStatus.DEPOSIT,
                dto.getBalance().toBigInteger() ,foundAccount.getBalance().toBigInteger());

        return response;
    }


    @Transactional(readOnly = true)
    public void validateAccount(Account account, BigDecimal amount, TransactionStatus transactionStatus) {
        // 적금 계좌일 경우 추가 검증
        if (account.getStatus().equals(AccountStatus.SAVINGS)) {
            if (transactionStatus == TransactionStatus.WITHDRAWAL) {
                throw new CommonException(HttpStatus.BAD_REQUEST, "적금 계좌는 출금이 불가합니다.");
            }
            
            // 날짜 확인
            account.getSavings().validDate();
        }
        // 출금 시 잔액 확인
        if (transactionStatus == TransactionStatus.WITHDRAWAL && account.getBalance().compareTo(amount) < 0) {
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    String.format("잔액부족 / 현재잔액 : %s원", account.getBalance().toBigInteger().toString()));
        }

    }

    public AccountDetailResponse getAccountDetail(String userName, String accountNumber) {
        // user
        User user = userService.getUserInfo(userName);

        // 해당 계좌 가져오기
        Account foundAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, "해당하는 계좌가 없습니다."));

        // history list 가져오기 
        List<AccountHistoryInfo> accountHistoryInfos = accountHistoryService.getAccountHistoryInfos(user.getId());

        // 반환값
        AccountDetailResponse response = new AccountDetailResponse(foundAccount.getAccountNumber(),
                foundAccount.getBankName(), foundAccount.getBalance().toBigInteger(), accountHistoryInfos);


        return response;
    }

    public AccountListResponse getUserAccounts(String userName) {
        // user
        User user = userService.getUserInfo(userName);

        // 유저 계좌 가져오기
        List<Account> foundAccounts = accountRepository.findAllByUserId(user.getId()).stream().toList();

        // 전체 금액 계산
        BigInteger totalBalance = foundAccounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add).toBigInteger(); // 모든 balance 합산

        // 일반 계좌 금액 계산
        BigInteger totalRegularBalance = foundAccounts.stream()
                .filter(fa -> fa.getStatus().equals(AccountStatus.REGULAR))
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add).toBigInteger();

        // 일반 계좌 금액 계산
        BigInteger totalSavingsBalance = foundAccounts.stream()
                .filter(fa -> fa.getStatus().equals(AccountStatus.SAVINGS))
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add).toBigInteger();

        // 계좌 목록
        List<AccountInfo> accountInfos = getAccountInfos(foundAccounts);

        // 반환값
        AccountListResponse response = new AccountListResponse(user.getName(), totalBalance,
                totalRegularBalance, totalSavingsBalance, accountInfos);

        return response;
    }

    public List<AccountInfo> getAccountInfos(List<Account> account) {
        List<AccountInfo> accountInfos = account.stream()
                .map(a -> new AccountInfo(
                        a.getBankName(),
                        a.getAccountNumber(),
                        a.getBalance().toBigInteger(),
                        a.getSavings() != null && !ObjectUtils.isEmpty(a.getSavings())
                        ? a.getSavings().getExpireDate() : null))
                .toList();

        return accountInfos;
    }

}
