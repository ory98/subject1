package emcast.subject.dto.service;

import emcast.subject.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionDto {

    private String userName;

    private String accountNumber;

    private TransactionStatus status;

    private BigDecimal balance;

    private String memo;
}
