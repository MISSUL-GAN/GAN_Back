package gan.missulgan.tag.exception;

import gan.missulgan.common.exception.BadRequestException;

public class BadTagException extends BadRequestException {

	public BadTagException() {
		super("잘못된 태그입니다");
	}
}
