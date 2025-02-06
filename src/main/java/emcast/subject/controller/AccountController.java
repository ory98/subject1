package emcast.subject.controller;

import emcast.subject.dto.ApiResponse;
import emcast.subject.dto.controller.AccountDetailRequest;
import emcast.subject.dto.controller.TransactionRequest;
import emcast.subject.dto.service.*;
import emcast.subject.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private AccountService accountService;

    // 입금(일반, 적금)
    @PutMapping("/deposit")
    public ApiResponse<TransactionResponse> deposit(@Valid @RequestBody TransactionRequest request) {
        TransactionDto transactionDto = new TransactionDto(request.getUserName(), request.getAccountNumber(),
                request.getStatus(), request.getBalance());

        TransactionResponse response = accountService.deposit(transactionDto);
        return ApiResponse.success(response);
    }

    // 출금(일반)
    @PutMapping("/withdrawal")
    public ApiResponse<TransactionResponse> withdrawal(@Valid @RequestBody TransactionRequest request) {
        TransactionDto transactionDto = new TransactionDto(request.getUserName(), request.getAccountNumber(),
                request.getStatus(), request.getBalance());

        TransactionResponse response = accountService.withdrawal(transactionDto);
        return ApiResponse.success(response);
    }

    // 단일 조회(내정보,계좌번호,은행명 입력 > 잔액,history 조회)
    @GetMapping("/user/account")
    public ApiResponse<AccountDetailResponse> getAccountDetail(@Valid @RequestParam AccountDetailRequest request) {
        AccountDetailResponse response = accountService.getAccountDetail(request.getUserName(),
                request.getAccountNumber(), request.getBankName());
        return ApiResponse.success(response);
    }

    // 리스트 조회(내정보 > 계좌번호,은행,잔액 조회)
    @GetMapping("/user/accounts")
    public ApiResponse<AccountListResponse> getUserAccounts(@RequestParam String userName) {
        AccountListResponse response = accountService.getUserAccounts(userName);
        return ApiResponse.success(response);
    }
}