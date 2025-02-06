package emcast.subject.repository;

import emcast.subject.domain.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {

    @Query(value = "select ah from AccountHistory ah where ah.user.id = :userId")
    List<AccountHistory> findAllByUserId(@Param("userId") Long userId);
}
