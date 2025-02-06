package emcast.subject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class CommonException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
}
