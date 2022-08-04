package gan.missulgan.member.domain;

import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.member.exception.MemberNotFoundException;
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
			.orElseThrow(() -> new MemberNotFoundException(ExceptionEnum.NO_SUCH_USER));
	}

	public MemberDTO findUser(String email) {
		return memberRepository.findByAccountEmail(email)
			.map(MemberDTO::from)
			.orElseThrow(() -> new RuntimeException("NO_SUCH_USER")); // TODO: replace with 400
	}
}
