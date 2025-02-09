package emcast.subject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "account_history")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_history_id")
    private Long id;

    private String memo;

    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private TransactionStatus status;

    private LocalDate transTime;

    public static AccountHistory createHistory(String memo, BigDecimal amount, Account account, User user, TransactionStatus status) {
        if ("".equals(memo)) memo = user.getName(); // 빈값일 경우 사용자 이름 삽입

        return AccountHistory.builder()
                .memo(memo)
                .amount(amount)
                .account(account)
                .user(user)
                .status(status)
                .transTime(LocalDate.now())
                .build();
    }

}
