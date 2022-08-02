package gan.missulgan.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends ApiException  {
    public UnauthorizedException(ExceptionEnum e) {
        super(e);
    }
}
