package gan.missulgan.image.domain.strategy.store;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import gan.missulgan.image.domain.ImageType;

@DisplayName("IPFS 저장 전략 테스트")
@SpringBootTest
class IPFSStoreStrategyTest {

	private static final String BUFFER_FILENAME = "bafkreie5z6l2dbhteyr5cgttcjgoxgnfocnqqnzb5b4kc3ly6wlhdc5hwi";

	@Value("${app.nftStorageToken}")
	private String token;

	private RestTemplate restTemplate;
	private FileStoreStrategy storeStrategy;

	@BeforeEach
	void setUp() {
		this.restTemplate = new RestTemplateBuilder()
			.setReadTimeout(Duration.ofSeconds(20))
			.setConnectTimeout(Duration.ofSeconds(10))
			.build();
		this.storeStrategy = new IPFSStoreStrategy(restTemplate);
		ReflectionTestUtils.setField(storeStrategy, "NFT_STORAGE_ACCESS_TOKEN", token);
	}

	@Test
	@DisplayName("저장")
	void store() throws IOException {
		byte[] bufferExpected = {1, 1};
		String fileName = storeStrategy.store(bufferExpected, ImageType.PNG);
		System.out.println("fileName = " + fileName);
		assertThat(fileName).isNotBlank();
		assertThat(fileName).startsWith("b");
	}

	@Test
	@DisplayName("불러오기")
	void load() throws IOException {
		Resource resource = storeStrategy.load(BUFFER_FILENAME);
		byte[] bytes = resource.getInputStream().readAllBytes();
		assertThat(bytes).hasSize(2);
	}
}