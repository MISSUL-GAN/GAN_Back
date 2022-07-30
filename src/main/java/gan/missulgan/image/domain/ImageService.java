package gan.missulgan.image.domain;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gan.missulgan.image.domain.strategy.filename.FileNameStrategy;
import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;
import gan.missulgan.image.dto.ImagesDTO;
import gan.missulgan.image.dto.SavedImageDTO;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;
	private final MemberRepository memberRepository;
	private final FileNameStrategy fileNameStrategy;
	private final FileStoreStrategy fileStoreStrategy;

	// TODO: find member by authentication first (나중에)
	private Member getMember() {
		return memberRepository.findById(0L) // TODO: 실제 사용자로 변경
			.orElseThrow(NoSuchElementException::new); // TODO: replace with custom exception
	}

	public List<SavedImageDTO> save(List<MultipartFile> files) {
		Member member = getMember();
		ImagesDTO imagesDTO = new ImagesDTO(files, fileNameStrategy);
		imagesDTO.storeFilesWith(fileStoreStrategy);
		List<Image> images = imagesDTO.toEntities(member.getId());
		imageRepository.saveAll(images);

		return images.stream()
			.map(Image::toDTO)
			.collect(Collectors.toList());
	}

	public Resource load(String fileName) {
		return fileStoreStrategy.load(fileName);
	}
}
