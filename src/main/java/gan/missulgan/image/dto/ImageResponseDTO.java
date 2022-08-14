package gan.missulgan.image.dto;

import gan.missulgan.image.domain.Image;
import gan.missulgan.image.domain.ImageType;
import lombok.Getter;

@Getter
public class ImageResponseDTO {

    private final String fileName;
    private final ImageType type;

    private ImageResponseDTO(String fileName, ImageType type) {
        this.fileName = fileName;
        this.type = type;
    }

    public static ImageResponseDTO from(Image image) {
        return new ImageResponseDTO(image.getFileName(), image.getImageType());
    }
}
