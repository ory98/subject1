package emcast.subject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "savings")
@Getter
@NoArgsConstructor
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    private BigDecimal interestRate; // 이자율

    private LocalDate expireDate;

    public Savings(Account account, BigDecimal interestRate) {
        this.account = account;
        this.interestRate = interestRate;
        this.expireDate = LocalDate.now();
    }
}
