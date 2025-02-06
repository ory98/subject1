package emcast.subject.domain;

import emcast.subject.exception.CommonException;
import org.springframework.http.HttpStatus;

public enum TransactionStatus {
    DEPOSIT("입금"),
    WITHDRAWAL("출금");

    private final String koreanValue;

    TransactionStatus(String koreanValue) {
        if ("입금".equals(koreanValue) || "출금".equals(koreanValue)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, "입금 또는 출금만 입력할 수 있습니다.");
        }
        this.koreanValue = koreanValue;
    }

}
