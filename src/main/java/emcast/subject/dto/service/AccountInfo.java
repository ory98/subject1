package emcast.subject.dto.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AccountInfo {

    private String bankName;

    private String accountNumber;

    private BigDecimal balance;

    private LocalDate expireData; // 적금일 경우에만 나타남
}
