package gan.missulgan.drawing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TagDrawingSearchRequestDTO {

    @NotNull
    private Set<Long> tagIds;
}
