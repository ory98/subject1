package emcast.subject.domain;

import emcast.subject.exception.CommonException;
import org.springframework.http.HttpStatus;

public enum AccountStatus {
    REGULAR, // 입출금계좌
    SAVINGS; // 적금계좌
}
