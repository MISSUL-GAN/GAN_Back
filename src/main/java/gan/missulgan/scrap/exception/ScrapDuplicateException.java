package gan.missulgan.scrap.exception;

import gan.missulgan.common.exception.ForbiddenException;

public class ScrapDuplicateException extends ForbiddenException {

    private static final String MESSAGE = "스크랩은 한 번만 누를 수 있습니다";

    public ScrapDuplicateException() {
        super(MESSAGE);
    }
}

