package emcast.subject.service;

import emcast.subject.domain.*;
import emcast.subject.dto.service.*;
import emcast.subject.exception.CommonException;
import emcast.subject.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public TransactionResponse deposit(TransactionDto dto) {
        // User 정보 가져오기
        User user = userService.getUserInfo(dto.getUserName());

        // 해당 계좌가 있는지 확인
        Account foundAccount = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, "해당하는 계좌가 없습니다."));

        // 적금 계좌일 경우 만기 날짜 확인
        if (foundAccount.getStatus().equals(AccountStatus.SAVINGS)) {
            Savings savingsInfo = savingsService.getSavingsInfo(foundAccount.getId());
            if (savingsInfo.getExpireDate().isBefore(LocalDate.now())) {
                throw new CommonException(HttpStatus.BAD_REQUEST, "만기날짜가 지났습니다. 입금할 수 없습니다.");
            }
        }

        // 해당 계좌 금액 입금
        foundAccount.increaseBalance(dto.getBalance());

        // 계좌 내역 저장 accountHistory
        accountHistoryService.saveAccountHistory(dto.getMemo(), dto.getBalance(), foundAccount, user, TransactionStatus.DEPOSIT);

        // 반환값
        TransactionResponse response = new TransactionResponse(user.getName(),
                foundAccount.getBankName(), foundAccount.getAccountNumber(), TransactionStatus.DEPOSIT,
                dto.getBalance().toBigInteger() ,foundAccount.getBalance().toBigInteger());

        return response;
    }

    @Transactional
    public TransactionResponse withdrawal(TransactionDto dto) {
        // user
        User user = userService.getUserInfo(dto.getUserName());

        // 해당계좌 있는지 확인
        Account foundAccount = accountRepository.findByAccountNumber(dto.getAccountNumber())
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, "해당하는 계좌가 없습니다."));

        // 적금 계좌인지 확인
        if (foundAccount.getStatus().equals(AccountStatus.SAVINGS)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, "적금계좌는 출금이 불가합니다.");
        }

        // 해당 계좌의 금액과 출금하려는 금액 비교
        int result = foundAccount.getBalance().compareTo(dto.getBalance());
        if (result < 0) {
            throw new CommonException(HttpStatus.BAD_REQUEST, String.format("잔액부족 / 현재잔액 : %s원", foundAccount.getBalance().toBigInteger().toString()));
        }

        // 해당 계좌 금액 출금
        foundAccount.decreaseBalance(dto.getBalance());

        // 계좌 내역 저장
        accountHistoryService.saveAccountHistory(dto.getMemo(), dto.getBalance(), foundAccount, user, TransactionStatus.WITHDRAWAL);

        // 반환값
        TransactionResponse response = new TransactionResponse(user.getName(),
                foundAccount.getBankName(), foundAccount.getAccountNumber(), TransactionStatus.DEPOSIT,
                dto.getBalance().toBigInteger() ,foundAccount.getBalance().toBigInteger());

        return response;
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

        return new AccountListResponse();
    }

}
