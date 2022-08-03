package gan.missulgan.common.exception;

import gan.missulgan.common.ExceptionEnum;
import lombok.Getter;

@Getter
public class BadRequestException extends ApiException  {
    public BadRequestException(ExceptionEnum e) {
        super(e);
    }
}

