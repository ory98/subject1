package emcast.subject.service;

import emcast.subject.domain.*;
import emcast.subject.dto.service.*;
import emcast.subject.exception.CommonException;
import emcast.subject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserService userService;
    private final AccountHistoryService accountHistoryService;

    private final AccountRepository accountRepository;

    @Transactional
    public TransactionResponse processTransaction(TransactionDto dto) {
        // User 정보 가져오기
        User user = userService.getUserInfo(dto.getUserName());

        // 해당 계좌가 있는지 확인
        Account foundAccount = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, "해당하는 계좌가 없습니다."));

        // 계좌 상태 및 적금 검증
        validateAccount(foundAccount, dto.getBalance(), dto.getStatus());

        // 해당 계좌 거래
        if (dto.getStatus() == TransactionStatus.DEPOSIT) {
            foundAccount.increaseBalance(dto.getBalance());
        } else if (dto.getStatus() == TransactionStatus.WITHDRAWAL) {
            foundAccount.decreaseBalance(dto.getBalance());
        }

        // 계좌 내역 저장 accountHistory
        accountHistoryService.saveAccountHistory(dto.getMemo(), dto.getBalance(), foundAccount, user, TransactionStatus.DEPOSIT);

        return new TransactionResponse(user.getName(),
                foundAccount.getBankName(), foundAccount.getAccountNumber(), TransactionStatus.DEPOSIT,
                dto.getBalance().toBigInteger() ,foundAccount.getBalance().toBigInteger());
    }

    private void validateAccount(Account account, BigDecimal amount, TransactionStatus transactionStatus) {
        // 날짜 확인
        if (transactionStatus.equals(TransactionStatus.DEPOSIT)) {
            account.getSavings().validDate();
        }

        // 적금일 경우, 출금 불가
        if (transactionStatus.equals(TransactionStatus.WITHDRAWAL)) {
            account.validSavingsAccount();
        }

        // 출금 시 잔액 확인
        account.validBalance(amount);

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
         return new AccountDetailResponse(foundAccount.getAccountNumber(),
                foundAccount.getBankName(), foundAccount.getBalance().toBigInteger(), accountHistoryInfos);
    }

    public AccountListResponse getUserAccounts(String userName) {
        // user
        User user = userService.getUserInfo(userName);

        // 전체 금액 계산
        BigInteger totalBalance = user.calculateTotalBalance().toBigInteger();

        // 일반 계좌 금액 계산
        BigInteger totalRegularBalance = user.calculateTotalRegularBalance().toBigInteger();

        // 적금 계좌 금액 계산
        BigInteger totalSavingsBalance = user.calculateTotalSavingsBalance().toBigInteger();

        // 계좌 목록
        List<AccountInfo> accountInfos = getAccountInfos(user.getAccounts());

        // 반환값
        return new AccountListResponse(user.getName(), totalBalance,
                totalRegularBalance, totalSavingsBalance, accountInfos);

    }

    public List<AccountInfo> getAccountInfos(List<Account> account) {
        List<AccountInfo> accountInfos = account.stream()
                .map(a -> new AccountInfo(
                        a.getBankName(),
                        a.getAccountNumber(),
                        a.getBalance().toBigInteger(),
                        a.getExpireDate())
                ).toList();

        return accountInfos;
    }

}
