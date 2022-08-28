package gan.missulgan.nft.service;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.repository.DrawingRepository;
import gan.missulgan.image.domain.Image;
import gan.missulgan.image.domain.ImageType;
import gan.missulgan.image.repository.ImageRepository;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.domain.Role;
import gan.missulgan.member.repository.MemberRepository;
import gan.missulgan.nft.domain.ChainType;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@DisplayName("NFT 민팅 테스트")
@SpringBootTest
class NFTServiceTest {

    @Value("${app.nftAuthorizationKey}")
    private String key;

    private RestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DrawingRepository drawingRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private NFTService nftService;

    Drawing drawing;

    @BeforeEach
    void setUp() {
        this.restTemplate = new RestTemplateBuilder()
                .setReadTimeout(Duration.ofSeconds(20))
                .setConnectTimeout(Duration.ofSeconds(10))
                .build();
        this.nftService = new NFTService(restTemplate);
        ReflectionTestUtils.setField(nftService, "NFT_AUTHORIZATION_KEY", key);
        ReflectionTestUtils.setField(nftService, "CHAIN", ChainType.RINKEBY.getValue());
        ReflectionTestUtils.setField(nftService, "CONTRACT_ADDRESS", "0x534a77f27cddf844dd23664a387a98b13f4d7ca6");

        Member member = Member.builder()
            .profileImage("사용자1_프로필")
            .name("사1")
            .accountEmail("member1@com")
            .role(Role.USER)
            .provider("kakao")
            .build();

        Image image = Image.builder()
            .imageType(ImageType.PNG)
            .member(member)
            .fileName("bafkreiapdwp65rgye2f7isg4bg55mneudaouuuaugctw2qms2aqzr3eeii")
            .build();

        this.drawing = Drawing.builder()
            .title("MissulGAN")
            .description("MissulGAN LOGO")
            .image(image)
            .member(member)
            .build();
        memberRepository.save(member);
        imageRepository.save(image);
        drawingRepository.save(drawing);
    }

    // @Test
    @Transactional
    @DisplayName("민팅")
    void mint() {
        String walletAddress = "0xbae911aBE112EbeE9f81936a3Ed4B9934b7C70Cb";
        nftService.mint(drawing, walletAddress);
        String assetContractAddress = drawing.getNft().getAssetContractAddress();
        Assertions.assertThat(assetContractAddress).isEqualTo("0x534a77f27cddf844dd23664a387a98b13f4d7ca6");
    }

    // @Test
    @Transactional
    @DisplayName("트랜젝션 해쉬 값으로 NFT 정보 저장하기")
    void saveNFT() {
        String transactionHash = "0xdc38c4bed882efb3ed1bfe93cee54f6cd71521247cad96418250e2a24468d8a6";
        nftService.saveNFT(drawing, transactionHash);
        String assetContractAddress = drawing.getNft().getAssetContractAddress();
        Assertions.assertThat(assetContractAddress).isEqualTo("0x534a77f27cddf844dd23664a387a98b13f4d7ca6");
    }
}