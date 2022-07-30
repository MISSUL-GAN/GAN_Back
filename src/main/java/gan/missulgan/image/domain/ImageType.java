package gan.missulgan.image.domain;

import static org.springframework.http.MediaType.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {
	PNG(IMAGE_PNG_VALUE),
	JPEG(IMAGE_JPEG_VALUE),
	GIF(IMAGE_GIF_VALUE);

	private final String type;
}
