package emcast.subject.exception;

import emcast.subject.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionAdvice {

    @ExceptionHandler
    public ApiResponse handleCommonException(CommonException e) {
        return ApiResponse.fail(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler
    public ApiResponse handleNotValidException(MethodArgumentNotValidException e) {
        // 에러메세지 꺼내오기
        String message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("유효성 검사 실패");

        return ApiResponse.fail(message, HttpStatus.BAD_REQUEST);
    }
}
