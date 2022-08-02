package gan.missulgan.exception;

import lombok.Getter;

@Getter
public class ConflictException extends ApiException  {
    public ConflictException(ExceptionEnum e) {
        super(e);
    }
}
