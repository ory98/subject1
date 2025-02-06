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

    private Long accountId;

    private BigDecimal interestRate; // 이자율

    private LocalDate expireDate;

    public Savings(Long accountId, BigDecimal interestRate, LocalDate expireDate) {
        this.accountId = accountId;
        this.interestRate = interestRate;
        this.expireDate = expireDate;
    }
}
