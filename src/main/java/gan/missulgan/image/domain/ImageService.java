package gan.missulgan.image.domain;

import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;
import gan.missulgan.image.dto.ImageResponseDTO;
import gan.missulgan.image.exception.ImageNotFoundException;
import gan.missulgan.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final FileStoreStrategy fileStoreStrategy;

    @Transactional
    public ImageResponseDTO save(Member member, byte[] bytes, String contentType) {
        ImageType imageType = getImageType(contentType);
        Image imageToSave = Image.builder()
                .member(member)
                .imageType(imageType)
                .bytes(bytes)
                .build();
        Image savedImage = saveImage(imageToSave);
        return ImageResponseDTO.from(savedImage);
    }

    public Resource load(String fileName) {
        try {
            Image image = getImage(fileName);
            return image.load(fileStoreStrategy);
        } catch (IOException e) {
            throw new ImageNotFoundException();
        }
    }

    public Image getImage(String fileName) {
        return imageRepository.findImageByFileName(fileName)
                .orElseThrow(ImageNotFoundException::new);
    }

    private Image saveImage(Image imageToSave) {
        try {
            imageToSave.store(fileStoreStrategy);
            Optional<Image> imageOptional = imageRepository.findImageByFileName(imageToSave.getFileName());
            if (imageOptional.isEmpty())
                return imageRepository.save(imageToSave);
            return imageOptional.get();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage()); // TODO: 500 대체
        }
    }

    private ImageType getImageType(String contentType) {
        ImageType imageType = ImageType.of(contentType);
        if (imageType != null)
            return imageType;
        throw new RuntimeException("NOT_A_IMAGE"); // TODO: 400으로 대체
    }
}
