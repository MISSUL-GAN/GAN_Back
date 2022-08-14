package gan.missulgan.scrap.exception;

import gan.missulgan.common.exception.NotFoundException;

public class BadScrapException extends NotFoundException {

    private static final String MESSAGE = "해당 스크랩이 존재하지 않습니다";

    public BadScrapException() {
        super(MESSAGE);
    }
}
