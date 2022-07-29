package gan.missulgan.image.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gan.missulgan.entity.Member;
import gan.missulgan.image.domain.strategy.filename.FileNameStrategy;
import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;
import gan.missulgan.image.dto.ImagesDTO;
import gan.missulgan.image.dto.SavedImageDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;
	private final FileNameStrategy fileNameStrategy;
	private final FileStoreStrategy fileStoreStrategy;

	public List<SavedImageDTO> save(List<MultipartFile> files) {
		// TODO: find member by authentication first
		Member member = new Member();
		ImagesDTO imagesDTO = new ImagesDTO(files, fileNameStrategy);
		imagesDTO.storeFilesWith(fileStoreStrategy);
		List<Image> images = imagesDTO.toEntities(member.getId());
		imageRepository.saveAll(images);

		return images.stream()
			.map(Image::toDTO)
			.collect(Collectors.toList());
	}
}
