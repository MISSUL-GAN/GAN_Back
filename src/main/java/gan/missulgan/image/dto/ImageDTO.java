package gan.missulgan.image.dto;

import org.springframework.web.multipart.MultipartFile;

import gan.missulgan.image.domain.Image;
import gan.missulgan.image.domain.ImageType;
import gan.missulgan.image.domain.strategy.filename.FileNameStrategy;
import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;

public class ImageDTO {

	private final MultipartFile file;
	private final FileNameStrategy fileNameStrategy;

	public ImageDTO(MultipartFile file, FileNameStrategy fileNameStrategy) {
		validateTypeNotNull(file);
		validateValidType(file);
		this.file = file;
		this.fileNameStrategy = fileNameStrategy;
	}

	public void validateTypeNotNull(MultipartFile file) {
		if (file.getContentType() == null || file.getContentType().isEmpty())
			return; // TODO: throw predefined exception
	}

	private void validateValidType(MultipartFile file) {
		String contentType = file.getContentType();
		if (!ImageType.has(contentType))
			return; // TODO: throw pre-defined exception
	}

	public Image toEntity(Long memberId) {
		String originalFileName = file.getOriginalFilename();
		String encodedFileName = fileNameStrategy.encodeFileName(originalFileName);
		ImageType imageType = ImageType.of(file.getContentType());
		return Image.builder()
			.memberId(memberId)
			.fileName(originalFileName)
			.imageType(imageType)
			.storedFileName(encodedFileName)
			.build();
	}

	public void saveFileWith(FileStoreStrategy fileStoreStrategy) {
		String originalFileName = file.getOriginalFilename();
		String encodedFileName = fileNameStrategy.encodeFileName(originalFileName);
		fileStoreStrategy.store(file, encodedFileName);
	}
}
