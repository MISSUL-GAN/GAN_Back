package gan.missulgan.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionEnum {

    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001", "~~~"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002", "~~~"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003", "~~~"),

    SECURITY_01(HttpStatus.UNAUTHORIZED, "S0001", "권한이 없습니다."),

    BAD_REQUEST_EXCEPTION(HttpStatus.BAD_REQUEST, "---", "~~~"),
    CONFLICT_EXCEPTION(HttpStatus.CONFLICT, "---", "~~~"),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "----", "~~~"),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "---", "~~~"),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, "---", "~~~"),

    NO_SUCH_MEMBER(HttpStatus.BAD_REQUEST, "---", "해당 사용자가 존재하지 않습니다."),
    HEART_OWNER(HttpStatus.FORBIDDEN, "---", "본인 그림의 좋아요는 누를 수 없습니다."),
    HEART_DONE(HttpStatus.FORBIDDEN, "---", "좋아요를 이미 눌렀습니다."),
    NO_SUCH_HEART(HttpStatus.BAD_REQUEST, "---", "해당 좋아요가 존재하지 않습니다.");


    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
