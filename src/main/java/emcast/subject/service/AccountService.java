package emcast.subject.service;

import emcast.subject.dto.service.AccountDetailResponse;
import emcast.subject.dto.service.AccountListResponse;
import emcast.subject.dto.service.TransactionDto;
import emcast.subject.dto.service.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Transactional
    public TransactionResponse deposit(TransactionDto dto) {
        return new TransactionResponse();
    }

    @Transactional
    public TransactionResponse withdrawal(TransactionDto dto) {
        return new TransactionResponse();
    }

    public AccountDetailResponse getAccountDetail(String userName, String accountNumber, String bankName) {
        return new AccountDetailResponse();
    }

    public AccountListResponse getUserAccounts(String userName) {
        return new AccountListResponse();
    }

}
