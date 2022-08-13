package gan.missulgan.heart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class DrawingIdRequestDTO {

    @NotNull
    private Long drawingId;

}