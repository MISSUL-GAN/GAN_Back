package gan.missulgan;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.repository.DrawingRepository;
import gan.missulgan.image.domain.Image;
import gan.missulgan.image.domain.ImageRepository;
import gan.missulgan.image.domain.ImageType;
import gan.missulgan.member.domain.Role;
import gan.missulgan.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import gan.missulgan.member.domain.Member;


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

        private final MemberRepository memberRepository;
        private final DrawingRepository drawingRepository;
		private final ImageRepository imageRepository;

        public void setup() {

            Member member1 = Member.builder()
                    .profileImage("사용자1_프로필")
                    .userNickname("사1")
                    .accountEmail("member1@com")
                    .role(Role.USER)
                    .provider("kakao")
                    .build();

            Member member2 = Member.builder()
                    .profileImage("사용자2_프로필")
                    .userNickname("사2")
                    .accountEmail("member2@com")
                    .role(Role.USER)
                    .provider("kakao")
                    .build();

            Member member3 = Member.builder()
                .profileImage("http://k.kakaocdn.net/dn/dW2Llw/btrIc9jpg")
                .provider("kakao")
                .role(Role.USER)
                .accountEmail("parkcheol@kakao.com")
                .userNickname("성철")
                .build();

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

            memberRepository.save(member1);
            memberRepository.save(member2);
            memberRepository.save(member3);
			imageRepository.save(image);
            drawingRepository.save(drawing1);
			drawingRepository.save(drawing2);
        }

    }
}
