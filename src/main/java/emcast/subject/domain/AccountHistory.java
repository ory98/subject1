package emcast.subject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_history")
@Getter
@NoArgsConstructor
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private TransactionStatus status;

    private LocalDateTime transTime;

}
