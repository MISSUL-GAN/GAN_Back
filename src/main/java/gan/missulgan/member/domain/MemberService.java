package gan.missulgan.member.domain;

import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

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
}
