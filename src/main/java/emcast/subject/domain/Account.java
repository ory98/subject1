package emcast.subject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    public Account(User user, AccountStatus status, String accountNumber, String bankName, BigDecimal balance) {
        this.user = user;
        this.status = status;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.balance = balance;
    }
}
