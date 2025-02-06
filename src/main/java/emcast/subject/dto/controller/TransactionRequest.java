package emcast.subject.dto.controller;

import emcast.subject.domain.TransactionStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    private String userName;

    private String memo;

    @NotBlank(message = "계좌 번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]+$", message = "숫자만 입력 가능합니다.")
    private String accountNumber;

    @NotNull(message = "목적을 입력해주세요.")
    private TransactionStatus status;

    @NotNull(message = "잔액은 필수 입력값입니다.")
    @Positive(message = "0보다 큰 값을 입력해주세요.")
    @Digits(integer = 10, fraction = 0, message = "잔액은 소수점을 포함할 수 없습니다.")
    private BigDecimal balance;

}
