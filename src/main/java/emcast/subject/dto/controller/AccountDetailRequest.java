package emcast.subject.dto.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDetailRequest {

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "계좌 번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]+$", message = "숫자만 입력 가능합니다.")
    private String accountNumber;

}
