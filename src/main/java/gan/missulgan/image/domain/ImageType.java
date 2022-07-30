package gan.missulgan.image.domain;

import static org.springframework.http.MediaType.*;

import java.util.NoSuchElementException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageType {
	PNG(IMAGE_PNG_VALUE),
	JPEG(IMAGE_JPEG_VALUE),
	GIF(IMAGE_GIF_VALUE);

	private final String type;

	public static ImageType of(String type) throws NoSuchElementException {
		for (ImageType imageType : ImageType.values()) {
			if (imageType.getType().equals(type))
				return imageType;
		}
		throw new NoSuchElementException("Not a image type");
	}

	public static boolean has(String type) {
		for (ImageType imageType : ImageType.values()) {
			if (imageType.getType().equals(type))
				return true;
		}
		return false;
	}
}
