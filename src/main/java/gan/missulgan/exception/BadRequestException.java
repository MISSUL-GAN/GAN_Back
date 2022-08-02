package gan.missulgan.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends ApiException  {
    public BadRequestException(ExceptionEnum e) {
        super(e);
    }
}

