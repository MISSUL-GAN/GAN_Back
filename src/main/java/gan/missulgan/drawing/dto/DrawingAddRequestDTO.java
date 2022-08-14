package gan.missulgan.drawing.dto;

import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import gan.missulgan.nft.domain.Nft;
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
	private NftAddRequestDTO nft;

	public Optional<Nft> getNft() {
		if(nft != null)
			return Optional.of(nft.toEntity());
		return Optional.empty();
	}
}
