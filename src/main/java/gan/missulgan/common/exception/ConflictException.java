package gan.missulgan.common.exception;

import gan.missulgan.common.ExceptionEnum;
import lombok.Getter;

@Getter
public class ConflictException extends ApiException  {
    public ConflictException(ExceptionEnum e) {
        super(e);
    }
}
