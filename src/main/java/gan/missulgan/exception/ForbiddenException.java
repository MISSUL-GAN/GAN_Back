package gan.missulgan.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends ApiException  {
    public ForbiddenException(ExceptionEnum e) {
        super(e);
    }
}