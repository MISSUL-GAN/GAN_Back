package gan.missulgan.drawing.exception;

import gan.missulgan.common.exception.BadRequestException;

public class BadDrawingException extends BadRequestException {
	public BadDrawingException() {
		super("해당 작품이 존재하지 않습니다.");
	}
}
