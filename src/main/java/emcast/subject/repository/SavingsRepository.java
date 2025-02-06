package emcast.subject.repository;

import emcast.subject.domain.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

    @Query(value = "select s from Savings s where s.accountId = :accountId")
    Optional<Savings> findByAccountId(@Param("accountId") Long accountId);
}
