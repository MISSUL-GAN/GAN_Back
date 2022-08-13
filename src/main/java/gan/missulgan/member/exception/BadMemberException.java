package gan.missulgan.member.exception;

import gan.missulgan.common.exception.NotFoundException;

public class BadMemberException extends NotFoundException {
    public BadMemberException() {
        super("해당 사용자가 존재하지 않습니다.");
    }
}
