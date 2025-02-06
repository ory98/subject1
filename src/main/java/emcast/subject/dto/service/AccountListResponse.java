package emcast.subject.dto.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountListResponse {

    private String userName;

    private BigInteger totalBalance; // 전체 계좌를 합친 금액 (일반 + 적금)

    private BigInteger totalRegularBalance; // 전체 일반계좌를 합친 금액

    private BigInteger totalSavingsBalance; // 전체 적금계좌를 합친 금액

    private List<AccountInfo> accountInfos;
}
