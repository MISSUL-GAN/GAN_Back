package gan.missulgan.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends ApiException  {
    public NotFoundException(ExceptionEnum e) {
        super(e);
    }
}
