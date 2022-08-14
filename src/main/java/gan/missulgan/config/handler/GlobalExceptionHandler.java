package gan.missulgan.config.handler;

import gan.missulgan.common.dto.ExceptionDTO;
import gan.missulgan.common.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionDTO> handleException(final Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDTO.of(500, "INTERNAL_SERVER_ERROR", e.getMessage()));
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ExceptionDTO> handleApiException(final ApiException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(e.getStatus())
                .body(ExceptionDTO.of(e.getStatus().value(), e.getStatus().name(), e.getMessage()));
    }
}
