package emcast.subject.service;

import emcast.subject.domain.Savings;
import emcast.subject.exception.CommonException;
import emcast.subject.repository.SavingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SavingsService {

    private final SavingsRepository savingsRepository;

    @Transactional(readOnly = true)
    public Savings getSavingsInfo(Long accountId) {
        return savingsRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, "해당하는 적금 계좌가 없습니다."));

    }
}
