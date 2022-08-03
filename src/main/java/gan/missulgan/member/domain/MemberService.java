package gan.missulgan.member.domain;

import org.springframework.stereotype.Service;

import gan.missulgan.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public String findRole(String email) {
		return memberRepository.findByAccountEmail(email)
			.map(Member::getAccountEmail)
			.orElseThrow(() -> new RuntimeException("NO_SUCH_USER")); // TODO: replace with 400
	}

	public MemberDTO findUser(String email) {
		return memberRepository.findByAccountEmail(email)
			.map(MemberDTO::from)
			.orElseThrow(() -> new RuntimeException("NO_SUCH_USER")); // TODO: replace with 400
	}
}
