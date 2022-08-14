package gan.missulgan.heart.exception;

import gan.missulgan.common.exception.NotFoundException;

public class BadHeartException extends NotFoundException {

    private static final String MESSAGE = "해당 좋아요가 존재하지 않습니다";

    public BadHeartException() {
        super(MESSAGE);
    }
}