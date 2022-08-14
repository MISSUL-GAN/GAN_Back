package gan.missulgan.drawing.exception;

import gan.missulgan.common.exception.ForbiddenException;

public class DrawingOwnerException extends ForbiddenException {

	private static final String MESSAGE = "본인의 작품이 아닙니다";

	public DrawingOwnerException() {
		super(MESSAGE);
	}
}
