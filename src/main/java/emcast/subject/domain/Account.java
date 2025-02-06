package emcast.subject.domain;

import emcast.subject.exception.CommonException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private AccountStatus status;

    private String accountNumber;

    private String bankName;

    private BigDecimal balance;

    @OneToOne(mappedBy = "account")
    private Savings savings;

    public Account(User user, AccountStatus status, String accountNumber, String bankName, BigDecimal balance) {
        this.user = user;
        this.status = status;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.balance = balance;
    }

    public void increaseBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public void decreaseBalance(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public void validSavingsAccount() {
        if (status.equals(AccountStatus.SAVINGS)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, "적금계좌는 출금이 불가합니다.");
        }
    }
    public void validBalance(BigDecimal amount) {
        if (balance.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new CommonException(HttpStatus.BAD_REQUEST, String.format("잔액부족 / 현재잔액 : %s원", balance.toBigInteger()));
        }
    }

    public boolean isRegular() {
        return AccountStatus.REGULAR.equals(this.status);
    }

    public boolean isSavings() {
        return AccountStatus.SAVINGS.equals(this.status);
    }

    public LocalDate getExpireDate() {
        if (savings == null || ObjectUtils.isEmpty(savings)) {
            return null;
        }
        return savings.getExpireDate(); // 첫 번째 Savings의 만료 날짜 반환
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
