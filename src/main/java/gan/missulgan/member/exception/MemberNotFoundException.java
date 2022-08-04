package gan.missulgan.member.exception;

import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.common.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(ExceptionEnum e) {
        super(e);
    }
}
