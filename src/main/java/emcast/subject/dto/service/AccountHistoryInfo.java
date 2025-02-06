package emcast.subject.dto.service;

import emcast.subject.domain.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountHistoryInfo {

    private String subject;

    private BigDecimal amount;

    private TransactionStatus status;

    private LocalDateTime time;
}
