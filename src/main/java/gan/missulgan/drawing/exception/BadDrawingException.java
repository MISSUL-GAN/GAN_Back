package gan.missulgan.drawing.exception;

import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.common.exception.BadRequestException;

public class BadDrawingException extends BadRequestException {
	public BadDrawingException() {
		super(ExceptionEnum.BAD_REQUEST_EXCEPTION);
	}
}
