package emcast.subject.dto.service;

import emcast.subject.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailResponse {

    private String accountNumber;

    private String bankName;

    private BigInteger balance;

    private List<AccountHistoryInfo> accountHistoryInfos;

}
