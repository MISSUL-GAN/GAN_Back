package gan.missulgan.heart.dto;

import gan.missulgan.heart.domain.Heart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class HeartRequestDTO {
    private final Long memberId;
    private final Long drawingId;

    @Builder
    public static HeartRequestDTO from(Heart heart) {
        return HeartRequestDTO.builder()
                .memberId(heart.getMember().getId())
                .drawingId(heart.getDrawing().getId())
                .build();
    }
}
