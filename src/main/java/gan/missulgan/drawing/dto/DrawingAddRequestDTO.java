package gan.missulgan.drawing.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
