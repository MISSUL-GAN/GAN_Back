package gan.missulgan.common.exception;

import gan.missulgan.common.ExceptionEnum;
import lombok.Getter;

@Getter
public class UnauthorizedException extends ApiException  {
    public UnauthorizedException(ExceptionEnum e) {
        super(e);
    }
}
