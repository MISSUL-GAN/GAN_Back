package gan.missulgan.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends ApiException  {
    public NotFoundException(String message) {
        super(NOT_FOUND, message);
    }
}
