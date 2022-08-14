package gan.missulgan.image.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

import static org.springframework.http.MediaType.*;

@Getter
@AllArgsConstructor
public enum ImageType {
    PNG(IMAGE_PNG_VALUE),
    JPEG(IMAGE_JPEG_VALUE),
    GIF(IMAGE_GIF_VALUE);

    private final String contentType;

    public static ImageType of(String contentType) throws NoSuchElementException {
        for (ImageType imageType : ImageType.values()) {
            if (imageType.getContentType().equals(contentType))
                return imageType;
        }
        return null;
    }
}
