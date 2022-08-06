package gan.missulgan.drawing.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DrawingTagSearchRequestDTO {

	private Set<Long> tagIds;
}
