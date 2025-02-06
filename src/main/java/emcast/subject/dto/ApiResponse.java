package emcast.subject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private Result result;
    private HttpStatus status;
    private String message;
    private T data;
    public enum Result {
        SUCCESS, FAIL
    }

    public static <T> ApiResponse<T> success(T data) {
        return (ApiResponse<T>) ApiResponse.builder()
                .result(Result.SUCCESS)
                .status(HttpStatus.OK)
                .data(data)
                .message(null)
                .build();
    }

    public static <T> ApiResponse<T> fail(String message, HttpStatus status) {
        return (ApiResponse<T>) ApiResponse.builder()
                .result(Result.FAIL)
                .status(status)
                .message(message)
                .build();
    }
}
