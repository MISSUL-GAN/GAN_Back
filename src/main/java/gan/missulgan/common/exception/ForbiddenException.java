package gan.missulgan.common.exception;

import gan.missulgan.common.ExceptionEnum;
import lombok.Getter;

@Getter
public class ForbiddenException extends ApiException  {
    public ForbiddenException(ExceptionEnum e) {
        super(e);
    }
}