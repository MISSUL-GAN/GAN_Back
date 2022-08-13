package gan.missulgan.heart.exception;

import gan.missulgan.common.exception.NotFoundException;

public class BadHeartException extends NotFoundException {
    public BadHeartException() {
        super("해당 좋아요가 존재하지 않습니다.");
    }
}