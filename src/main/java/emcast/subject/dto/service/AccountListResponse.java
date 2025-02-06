package emcast.subject.dto.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountListResponse {

    private String userName;

    private BigDecimal totalBalance; // 전체 계좌를 합친 금액 (일반 + 적금)

    private BigDecimal totalRegularBalance; // 전체 일반계좌를 합친 금액

    private BigDecimal totalSavingsBalance; // 전체 적금계좌를 합친 금액

    private List<AccountInfo> accountInfos;
}
