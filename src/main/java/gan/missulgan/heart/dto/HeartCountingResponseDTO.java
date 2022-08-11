package gan.missulgan.heart.dto;

import gan.missulgan.drawing.domain.Drawing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class HeartCountingResponseDTO {
    private final Long heartCounting;

    @Builder
    public static HeartCountingResponseDTO from(Drawing drawing) {
        return HeartCountingResponseDTO.builder()
                .heartCounting(drawing.getHeartCounting())
                .build();
    }
}
