package gan.missulgan.drawing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DrawingAddRequestDTO {

    @NotBlank
    private String title;

    @NotNull
    private String description;

    @NotBlank
    private String fileName;

    @NotNull
    @Size(min = 1, max = 3)
    private Set<Long> tagIds;

    private String walletAddress;

}
