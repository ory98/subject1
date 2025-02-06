package emcast.subject.dto.service;

import emcast.subject.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private String userName;

    private String bankName;

    private String accountNumber;

    private TransactionStatus status;

    private BigInteger balance; // 변경된 금액

    private BigInteger currentTotalBalance; // 현재 잔여 잔액
}
