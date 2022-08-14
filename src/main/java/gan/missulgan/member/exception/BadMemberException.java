package gan.missulgan.member.exception;

import gan.missulgan.common.exception.NotFoundException;

public class BadMemberException extends NotFoundException {

    private static final String MESSAGE = "해당 사용자가 존재하지 않습니다";

    public BadMemberException() {
        super(MESSAGE);
    }
}
