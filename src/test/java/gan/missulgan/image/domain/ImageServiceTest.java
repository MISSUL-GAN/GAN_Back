package gan.missulgan.image.domain;

import static gan.missulgan.image.domain.ImageType.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import gan.missulgan.image.domain.strategy.name.FileNameStrategy;
import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;
import gan.missulgan.image.dto.ImageResponseDTO;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("이미지 서비스 테스트")
class ImageServiceTest {

	private static final String TEST_FILE_NAME = "testFileName";
	private static final String TEST_EMAIL = "test@emai.il";
	private static final byte[] EMPTY_BYTES = {};

	@InjectMocks
	private ImageService imageService;
	@Mock
	private ImageRepository imageRepository;
	@Mock
	private MemberRepository memberRepository;
	@Mock
	private FileNameStrategy fileNameStrategy;
	@Mock
	private FileStoreStrategy fileStoreStrategy;

	@BeforeEach
	void setUp() throws IOException {
		given(memberRepository.findByAccountEmail(anyString()))
			.willReturn(Optional.of(new Member()));
		given(imageRepository.findImageByFileName(any()))
			.willReturn(Optional.of(new Image()));

		MockMultipartFile mockMultipartFile = new MockMultipartFile(TEST_FILE_NAME, EMPTY_BYTES);
		given(fileNameStrategy.encodeName(any()))
			.willReturn(TEST_FILE_NAME);
		given(fileStoreStrategy.load(any()))
			.willReturn(mockMultipartFile.getResource());
		doNothing()
			.when(fileStoreStrategy)
			.store(any(), anyString());
	}

	@Test
	@DisplayName("저장/불러오기")
	void saveAndLoad() {
		// given, when
		ImageResponseDTO saved = imageService.save(TEST_EMAIL, EMPTY_BYTES, PNG.getContentType());
		Resource load = imageService.load(saved.getFileName());
		// then
		assertThat(load).isNotNull();
	}
}