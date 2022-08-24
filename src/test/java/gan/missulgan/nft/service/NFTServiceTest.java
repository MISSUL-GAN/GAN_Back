package gan.missulgan.nft.service;

import gan.missulgan.drawing.dto.MintResponseDTO;
import gan.missulgan.nft.domain.ChainType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;

@DisplayName("NFT 민팅 테스트")
@SpringBootTest
class NFTServiceTest {

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
        ReflectionTestUtils.setField(nftService, "CHAIN", ChainType.RINKEBY.getValue());
        ReflectionTestUtils.setField(nftService, "CONTRACT_ADDRESS", "0x534a77f27cddf844dd23664a387a98b13f4d7ca6");
    }

//    @Test
    @Transactional
    @DisplayName("민팅")
    void mintNFT() throws IOException {
        String name = "MissulGAN";
        String description = "MissulGAN LOGO";
        String fileUrl = "https://ipfs.io/ipfs/bafkreiatvoc5ruqisgnodrzimmbnt2jgcc5vrgvkh6ktinoh75y4n3gq4e";
        String walletAddress = "0xbae911aBE112EbeE9f81936a3Ed4B9934b7C70Cb";
        MintResponseDTO mintDTO = nftService.mintNFT(name, description, fileUrl, walletAddress);
        String transactionExternalUrl = mintDTO.getTransactionExternalUrl();
        Assertions.assertThat(transactionExternalUrl).startsWith("https://rinkeby.etherscan.io/tx/0x");
    }
}