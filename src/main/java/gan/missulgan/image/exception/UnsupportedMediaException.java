package gan.missulgan.image.exception;

import gan.missulgan.common.exception.ApiException;

import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

public class UnsupportedMediaException extends ApiException {

    private static final String MESSAGE = "지원하지 않는 미디어 타입입니다";

    public UnsupportedMediaException() {
        super(UNSUPPORTED_MEDIA_TYPE, MESSAGE);
    }
}
