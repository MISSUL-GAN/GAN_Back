package gan.missulgan.common.exception;

import gan.missulgan.common.ExceptionEnum;
import lombok.Getter;

@Getter
public class NotFoundException extends ApiException  {
    public NotFoundException(ExceptionEnum e) {
        super(e);
    }
}
