package emcast.subject.repository;

import emcast.subject.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    @Query(value = "select a from Account a where a.user.id = :userId")
    List<Account> findAllByUserId(@Param("userId") Long userId);
}
