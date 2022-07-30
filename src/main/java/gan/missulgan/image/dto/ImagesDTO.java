package gan.missulgan.image.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import gan.missulgan.image.domain.Image;
import gan.missulgan.image.domain.strategy.filename.FileNameStrategy;
import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;

public class ImagesDTO {

	private final List<ImageDTO> imageDTOs;

	public ImagesDTO(List<MultipartFile> files, FileNameStrategy fileNameStrategy) {
		this.imageDTOs = files.stream()
			.map(file -> new ImageDTO(file, fileNameStrategy))
			.collect(Collectors.toList());
	}

	public List<Image> toEntities(Long memberId) {
		return imageDTOs.stream()
			.map(dto -> dto.toEntity(memberId))
			.collect(Collectors.toList());
	}

	public void storeFilesWith(FileStoreStrategy fileStoreStrategy) {
		for (ImageDTO imageDTO : imageDTOs)
			imageDTO.saveFileWith(fileStoreStrategy);
	}
}
