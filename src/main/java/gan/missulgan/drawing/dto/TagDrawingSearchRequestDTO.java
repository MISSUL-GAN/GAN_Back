package gan.missulgan.drawing.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TagDrawingSearchRequestDTO {

	@NotNull
	@Size(min = 1, max = 3)
	private Set<Long> tagIds;
}