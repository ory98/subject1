package emcast.subject.dto.service;

import emcast.subject.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AccountHistoryInfo {

    private String memo;

    private BigInteger amount;

    private TransactionStatus status;

    private LocalDate date;
}
