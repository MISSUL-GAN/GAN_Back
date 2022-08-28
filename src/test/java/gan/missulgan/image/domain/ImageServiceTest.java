package gan.missulgan.image.domain;

import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;
import gan.missulgan.image.dto.ImageResponseDTO;
import gan.missulgan.image.repository.ImageRepository;
import gan.missulgan.image.service.ImageService;
import gan.missulgan.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static gan.missulgan.image.domain.ImageType.PNG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("이미지 서비스 테스트")
class ImageServiceTest {

    private static final String TEST_FILE_NAME = "testFileName";
    private static final Member TEST_MEMBER = new Member();
    private static final byte[] EMPTY_BYTES = {};

    @InjectMocks
    private ImageService imageService;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private FileStoreStrategy fileStoreStrategy;

    @Test
    @DisplayName("저장, 없을 때")
    void save() {
        // given
        given(imageRepository.findImageByFileName(any()))
                .willReturn(Optional.empty());
        given(imageRepository.save(any()))
                .willReturn(new Image());
        // when
        ImageResponseDTO saved = imageService.save(TEST_MEMBER, EMPTY_BYTES, PNG.getContentType());
        // then
        assertThat(saved).isNotNull();
    }

    @Test
    @DisplayName("저장, 있을 때")
    void saveIfExists() {
        // given
        given(imageRepository.findImageByFileName(any()))
                .willReturn(Optional.of(new Image()));
        // when
        ImageResponseDTO saved = imageService.save(TEST_MEMBER, EMPTY_BYTES, PNG.getContentType());
        // then
        assertThat(saved).isNotNull();
    }

    @Test
    @DisplayName("불러오기, 있을 때")
    void load() throws IOException {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(TEST_FILE_NAME, EMPTY_BYTES);
        given(fileStoreStrategy.load(any()))
                .willReturn(mockMultipartFile.getResource());
        given(imageRepository.findImageByFileName(any()))
                .willReturn(Optional.of(new Image()));
        // when
        Resource load = imageService.load(TEST_FILE_NAME);
        // then
        assertThat(load).isNotNull();
    }

    @Test
    @DisplayName("불러오기, 없을 때")
    void loadIfNotExists() {
        // given
        given(imageRepository.findImageByFileName(any()))
                .willReturn(Optional.empty());
        // when, then
        assertThatThrownBy(() -> imageService.load(TEST_FILE_NAME));
    }
}