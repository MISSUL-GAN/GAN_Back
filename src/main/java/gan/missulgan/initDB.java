package gan.missulgan;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.repository.DrawingRepository;
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

            Drawing drawing1 = Drawing.builder()
                    .title("그림1_제목")
                    .description("그림1_상세")
                    .fileName("img.png")
                    .build();

            memberRepository.save(member1);
            memberRepository.save(member2);
            drawingRepository.save(drawing1);
        }

    }
}
