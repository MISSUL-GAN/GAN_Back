package gan.missulgan.nft.service;

import gan.missulgan.image.domain.ImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("IPFS 저장 전략 테스트")
@SpringBootTest
class NFTServiceTest {

    @Value("${app.nftStorageToken}")
    private String token;

    @Value("${app.nftAuthorizationKey}")
    private String key;

    private RestTemplate restTemplate;

    @InjectMocks
    private NFTService nftService;

    @BeforeEach
    @Transactional
    void setUp() {
        this.restTemplate = new RestTemplateBuilder()
                .setReadTimeout(Duration.ofSeconds(20))
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();
        this.nftService = new NFTService(restTemplate);
        ReflectionTestUtils.setField(nftService, "NFT_AUTHORIZATION_KEY", key);
        ReflectionTestUtils.setField(nftService, "NFT_STORAGE_ACCESS_TOKEN", token);
    }

    @Test
    @Transactional
    @DisplayName("이미지를 IPFS로 저장")
    void imageToIPFS() throws IOException {
        byte[] bufferExpected = {1, 1};
        Resource ipfs = nftService.imageToIPFS(bufferExpected, ImageType.PNG);
        System.out.println("image_ipfs = " + ipfs);
        assertThat(ipfs).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("메타데이터를 IPFS로 저장")
    void fileToIPFS() throws IOException {
        String name = "MissulGAN";
        String description = "MissulGAN LOGO";
        String file_url = "https://ipfs.io/ipfs/bafkreiapdwp65rgye2f7isg4bg55mneudaouuuaugctw2qms2aqzr3eeii";
        String ipfs = nftService.metadataToIPFS(name, description, file_url);
        System.out.println("metadata_ipfs = " + ipfs);
        assertThat(ipfs).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("민팅")
    void minting() throws IOException {
        String contract_address = "0x6b3E6b7276EeB3Cd3A0E62BBC9f60351256b0988";
        String metadata_uri = "ipfs://bafkreics74xcppy4lt4tsedhejhfldkli3qcy7jnvjrbkkihh4hahtvlxe";
        String mint_to_address = "0x4e614dfb5b65705b3b7e48a1dac453362902596f";
        HttpStatus status = nftService.minting(contract_address, metadata_uri, mint_to_address);
        assertThat(status).isEqualTo(HttpStatus.CREATED);
    }
}