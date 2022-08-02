package gan.missulgan.exception.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@Data
public class ExceptionDto {
    private String errorCode;
    private String errorMessage;

    @Builder
    public ExceptionDto(HttpStatus status, String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

/**
 * 예시
 * {
 *   "errorCode": "S0001",
 *   "errorMessage": "권한이 없습니다."
 * }
 */