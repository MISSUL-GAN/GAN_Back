package gan.missulgan.heart.exception;

import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.common.exception.NotFoundException;

public class HeartNotFoundException extends NotFoundException {
    public HeartNotFoundException() {
        super(ExceptionEnum.NO_SUCH_HEART);
    }
}