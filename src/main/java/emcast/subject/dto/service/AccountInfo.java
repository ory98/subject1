package emcast.subject.dto.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AccountInfo {

    private String bankName;

    private String accountNumber;

    private BigInteger balance;

    private LocalDate expireData; // 적금일 경우에만 나타남
}
