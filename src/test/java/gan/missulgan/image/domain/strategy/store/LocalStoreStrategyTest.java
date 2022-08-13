package gan.missulgan.image.domain.strategy.store;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import gan.missulgan.image.domain.ImageType;

@DisplayName("로컬 저장소 전략 테스트")
class LocalStoreStrategyTest {

	private static final String TEST_FILE_NAME = "testFilename";
	private static final String CURRENT_DIRECTORY = ".";

	private final LocalStoreStrategy localStoreStrategy = new LocalStoreStrategy(bytes -> TEST_FILE_NAME);
	private File testFile;

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(localStoreStrategy, "storePath", ".");
		testFile = new File(CURRENT_DIRECTORY + File.separator + TEST_FILE_NAME);
		testFile.deleteOnExit();
	}

	@Test
	@DisplayName("저장/불러오기")
	void storeAndLoad() {
		// given
		byte[] bufferExpected = {1, 1};
		// when, then
		assertThatNoException().isThrownBy(() ->
			localStoreStrategy.store(bufferExpected, ImageType.PNG));
		assertThatNoException().isThrownBy(() ->
			localStoreStrategy.load(TEST_FILE_NAME));
		assertThat(testFile).hasSize(bufferExpected.length);
	}
}