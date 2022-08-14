package gan.missulgan.scrap.exception;

import gan.missulgan.common.exception.ForbiddenException;

public class ScrapOwnerException extends ForbiddenException {

    private static final String MESSAGE = "본인 작품에는 스크랩을 누를 수 없습니다";

    public ScrapOwnerException() {
        super(MESSAGE);
    }
}
