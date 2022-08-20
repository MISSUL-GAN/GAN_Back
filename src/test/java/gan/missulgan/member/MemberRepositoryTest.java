package gan.missulgan.member;

import gan.missulgan.member.domain.Member;
import gan.missulgan.member.domain.Role;
import gan.missulgan.member.exception.BadMemberException;
import gan.missulgan.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    Member member;

    @BeforeEach
    public void setUp() {
        member = new Member(Role.USER, "kakao", "image", "siyeon44@hanmail.net",
            "시연");
    }

    @Test
    @Transactional
    public void find_member_by_email() throws RuntimeException {
        memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findByAccountEmail("siyeon44@hanmail.net");
        if (findMember.isPresent()) {
            assertThat(findMember.get().getId()).isEqualTo(member.getId());
        }
        else {
            throw new BadMemberException();
        }
    }
}
