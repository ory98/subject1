package emcast.subject;

import emcast.subject.domain.Account;
import emcast.subject.domain.AccountStatus;
import emcast.subject.domain.Savings;
import emcast.subject.domain.User;
import emcast.subject.repository.AccountRepository;
import emcast.subject.repository.SavingsRepository;
import emcast.subject.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final SavingsRepository savingsRepository;

    @PostConstruct
    public void setUp() {
        // user
        User user = new User("test");
        userRepository.save(user);

        // account
        List<Account> accounts = List.of(
                new Account(user, AccountStatus.REGULAR, "111", "우리은행", new BigDecimal(1_000_000)),
                new Account(user, AccountStatus.REGULAR, "222", "신한은행", new BigDecimal(2_000_000)),
                new Account(user, AccountStatus.SAVINGS, "333", "하나은행", new BigDecimal(3_000_000)),
                new Account(user, AccountStatus.REGULAR, "444", "하나은행", new BigDecimal(4_000_000))
        );
        accountRepository.saveAll(accounts);

        // savings
        Savings savings = new Savings(accounts.get(2).getId(), new BigDecimal(4.2), LocalDate.of(2025, 12, 31));
        savingsRepository.save(savings);

    }
}
