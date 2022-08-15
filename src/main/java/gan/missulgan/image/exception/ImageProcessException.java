package gan.missulgan.image.exception;

import static org.springframework.http.HttpStatus.*;

import gan.missulgan.common.exception.ApiException;

public class ImageProcessException extends ApiException {

	private static final String MESSAGE = "이미지 처리 중 오류가 발생했습니다";

	public ImageProcessException() {
		super(INTERNAL_SERVER_ERROR, MESSAGE);
	}
}
