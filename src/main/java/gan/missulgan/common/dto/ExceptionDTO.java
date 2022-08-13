package gan.missulgan.common.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class ExceptionDTO {
    private int status;
    private String error;
    private String message;

    public static ExceptionDTO of(int status, String error, String message) {
        return new ExceptionDTO(status, error, message);
    }
}