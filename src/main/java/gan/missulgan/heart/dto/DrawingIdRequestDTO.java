package gan.missulgan.heart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class DrawingIdRequestDTO {
    private final Long drawingId;
}