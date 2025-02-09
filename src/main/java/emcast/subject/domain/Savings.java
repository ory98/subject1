package emcast.subject.domain;

import emcast.subject.exception.CommonException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "savings")
@Getter
@NoArgsConstructor
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "savings_id")
    private Long id;

    private BigDecimal interestRate; // 이자율

    private LocalDate expireDate;

    public Savings(BigDecimal interestRate, LocalDate expireDate) {
        this.interestRate = interestRate;
        this.expireDate = expireDate;
    }

    public void validDate() {
        if (expireDate.isBefore(LocalDate.now())) {
            throw new CommonException(HttpStatus.BAD_REQUEST, "만기날짜가 지났습니다.");
        }
    }

}
