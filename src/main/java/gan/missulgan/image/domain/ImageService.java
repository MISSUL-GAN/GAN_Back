package gan.missulgan.image.domain;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gan.missulgan.image.domain.strategy.name.FileNameStrategy;
import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;
import gan.missulgan.image.dto.ImageResponseDTO;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;
	private final MemberRepository memberRepository;
	private final FileNameStrategy fileNameStrategy;
	private final FileStoreStrategy fileStoreStrategy;

	// TODO: find member by authentication first (나중에)
	private Member getMember(String email) {
		return memberRepository.findByAccountEmail(email) // TODO: 실제 사용자로 변경
			.orElseThrow(NoSuchElementException::new); // TODO: replace with custom exception
	}

	@Transactional
	public ImageResponseDTO save(String email, byte[] bytes, String contentType) {
		ImageType imageType = getImageType(contentType);
		String name = fileNameStrategy.encodeName(bytes);
		Member member = getMember(email);
		Image image = Image.builder()
			.member(member)
			.imageType(imageType)
			.fileName(name)
			.build();
		saveImage(name, bytes, image);
		return ImageResponseDTO.from(image);
	}

	public Resource load(String fileName) {
		try {
			if (doesImageExist(fileName))
				return fileStoreStrategy.load(fileName);
			return null;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage()); // TODO: 404로 대체
		}
	}

	private boolean doesImageExist(String fileName) {
		return imageRepository.findImageByFileName(fileName)
			.isPresent();
	}

	private ImageType getImageType(String contentType) {
		ImageType imageType = ImageType.of(contentType);
		if (imageType != null)
			return imageType;
		throw new RuntimeException("NOT_A_IMAGE"); // TODO: 400으로 대체
	}

	private void saveImage(String name, byte [] bytes, Image image) {
		try {
			Optional<Image> imageByFileName = imageRepository.findImageByFileName(name);
			if (imageByFileName.isEmpty())
				imageRepository.save(image);
			fileStoreStrategy.store(bytes, name);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage()); // TODO: 500 대체
		}
	}
}
