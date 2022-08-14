package gan.missulgan.image.exception;

import gan.missulgan.common.exception.NotFoundException;

public class ImageNotFoundException extends NotFoundException {

	private static final String MESSAGE = "이미지가 없습니다";

	public ImageNotFoundException() {
		super(MESSAGE);
	}
}
