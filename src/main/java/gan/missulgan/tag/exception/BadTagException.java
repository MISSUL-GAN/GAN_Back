package gan.missulgan.tag.exception;

import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.common.exception.BadRequestException;

public class BadTagException extends BadRequestException {

	public BadTagException() {
		super(ExceptionEnum.BAD_REQUEST_EXCEPTION);
	}
}
