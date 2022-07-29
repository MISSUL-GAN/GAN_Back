package gan.missulgan.image.dto;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import gan.missulgan.image.domain.Image;
import gan.missulgan.image.domain.ImageType;
import gan.missulgan.image.domain.strategy.filename.FileNameStrategy;
import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;

public class ImageDTO {

	private final MultipartFile file;
	private final FileNameStrategy fileNameStrategy;

	public ImageDTO(MultipartFile file, FileNameStrategy fileNameStrategy) {
		validateType(file);
		this.file = file;
		this.fileNameStrategy = fileNameStrategy;
	}

	// TODO: throw pre-defined exception
	void validateType(MultipartFile file) {
		String contentType = file.getContentType();
		ImageType[] imageTypes = ImageType.values();
		boolean isValidType = Arrays.stream(imageTypes)
			.map(ImageType::getType)
			.anyMatch(type -> type.equals(contentType));
		if (!isValidType)
			return;
	}

	public Image toEntity(Long memberId) {
		String originalFileName = file.getOriginalFilename();
		String encodedFileName = fileNameStrategy.encodeFileName(originalFileName);
		ImageType imageType = ImageType.valueOf(file.getContentType());
		return Image.builder()
			.memberId(memberId)
			.fileName(originalFileName)
			.imageType(imageType)
			.fileName(encodedFileName)
			.build();
	}

	public void saveFileWith(FileStoreStrategy fileStoreStrategy) {
		String originalFileName = file.getOriginalFilename();
		String encodedFileName = fileNameStrategy.encodeFileName(originalFileName);
		fileStoreStrategy.store(file, encodedFileName);
	}
}
