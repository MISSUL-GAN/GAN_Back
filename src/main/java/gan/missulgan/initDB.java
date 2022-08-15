package gan.missulgan;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.repository.DrawingRepository;
import gan.missulgan.image.domain.Image;
import gan.missulgan.image.repository.ImageRepository;
import gan.missulgan.image.domain.ImageType;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.domain.Role;
import gan.missulgan.member.repository.MemberRepository;
import gan.missulgan.tag.domain.Tag;
import gan.missulgan.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class initDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.setup();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private static final List<String> tagNames = List.of("어두운", "화사한", "다채로운", "차분한", "강렬한", "차가운", "따뜻한", "반 고흐",
                "클로드 모네", "폴 세잔", "우키요에", "풍경", "동물", "인물", "기타");

        private final MemberRepository memberRepository;
        private final DrawingRepository drawingRepository;
        private final ImageRepository imageRepository;
        private final TagRepository tagRepository;

        public void setup() {

            Member member1 = Member.builder()
                    .profileImage("사용자1_프로필")
                    .name("사1")
                    .accountEmail("member1@com")
                    .role(Role.USER)
                    .provider("kakao")
                    .build();

            Member member2 = Member.builder()
                    .profileImage("사용자2_프로필")
                    .name("사2")
                    .accountEmail("member2@com")
                    .role(Role.USER)
                    .provider("kakao")
                    .build();

            Member member3 = Member.builder()
                    .profileImage("http://k.kakaocdn.net/dn/dW2Llw/btrIc9jpg")
                    .provider("kakao")
                    .role(Role.USER)
                    .accountEmail("parkcheol@kakao.com")
                    .name("성철")
                    .build();

            List<Tag> tags = tagNames.stream()
                    .map(Tag::new)
                    .collect(Collectors.toList());
            Image image = Image.builder()
                    .imageType(ImageType.PNG)
                    .member(member3)
                    .fileName("bafkreiapdwp65rgye2f7isg4bg55mneudaouuuaugctw2qms2aqzr3eeii")
                    .build();

            Drawing drawing1 = Drawing.builder()
                    .title("그림1_제목")
                    .description("그림1_상세")
                    .image(image)
                    .member(member3)
                    .build();

            Drawing drawing2 = Drawing.builder()
                    .title("그림2_제목")
                    .description("그림2_상세")
                    .image(image)
                    .member(member1)
                    .build();

            tagRepository.saveAll(tags);
            memberRepository.save(member1);
            memberRepository.save(member2);
            memberRepository.save(member3);
            imageRepository.save(image);
            drawingRepository.save(drawing1);
            drawingRepository.save(drawing2);
        }

    }
}