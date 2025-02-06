package emcast.subject.dto.controller;

import emcast.subject.domain.TransactionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "계좌 번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]+$", message = "숫자만 입력 가능합니다.")
    private String accountNumber;

    @NotNull(message = "목적을 입력해주세요.")
    private TransactionStatus status;

    @NotNull(message = "잔액은 필수 입력값입니다.")
    @Pattern(regexp = "^[1-9][0-9]*$", message = "0 이상의 숫자만 입력해주세요.")
    private BigDecimal balance;

}
