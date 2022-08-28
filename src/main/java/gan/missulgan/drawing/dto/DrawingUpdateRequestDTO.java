package gan.missulgan.drawing.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DrawingUpdateRequestDTO {

    @NotBlank
    private String title;

    @NotNull
    private String description;

    @NotNull
    @Size(min = 1, max = 4)
    private Set<Long> tagIds;

}
