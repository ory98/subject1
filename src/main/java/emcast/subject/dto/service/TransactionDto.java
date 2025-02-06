package emcast.subject.dto.service;

import emcast.subject.domain.TransactionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
}
