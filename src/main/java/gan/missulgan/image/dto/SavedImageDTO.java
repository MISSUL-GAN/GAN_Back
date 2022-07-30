package gan.missulgan.image.dto;

import gan.missulgan.image.domain.ImageType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SavedImageDTO {

	private final String fileName;
	private final ImageType type;

	@Builder
	public SavedImageDTO(String fileName, ImageType type) {
		this.fileName = fileName;
		this.type = type;
	}
}
