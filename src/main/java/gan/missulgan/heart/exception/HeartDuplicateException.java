package gan.missulgan.heart.exception;

import gan.missulgan.common.exception.ForbiddenException;

public class HeartDuplicateException extends ForbiddenException {

    private static final String MESSAGE = "좋아요는 한 번만 누를 수 있습니다";

    public HeartDuplicateException() {
        super(MESSAGE);
    }
}

