package gan.missulgan.tag.exception;

import gan.missulgan.common.exception.BadRequestException;

public class BadTagException extends BadRequestException {

    private static final String MESSAGE = "잘못된 태그입니다";

    public BadTagException() {
        super(MESSAGE);
    }
}
