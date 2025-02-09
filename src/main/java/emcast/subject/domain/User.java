package emcast.subject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    // 전체 금액 계산
    public BigDecimal calculateTotalBalance() {
        return accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 일반 계좌 금액 계산
    public BigDecimal calculateTotalRegularBalance() {
        return accounts.stream()
                .filter(Account::isRegular)
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 적금 계좌 금액 계산
    public BigDecimal calculateTotalSavingsBalance() {
        return accounts.stream()
                .filter(Account::isSavings)
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
