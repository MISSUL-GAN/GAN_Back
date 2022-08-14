package gan.missulgan.heart.exception;

import gan.missulgan.common.exception.ForbiddenException;

public class HeartOwnerException extends ForbiddenException {

    private static final String MESSAGE = "본인 작품에는 좋아요를 누를 수 없습니다";

    public HeartOwnerException() {
        super(MESSAGE);
    }
}
