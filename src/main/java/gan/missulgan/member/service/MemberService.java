package gan.missulgan.member.service;

import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.repository.MemberRepository;
import gan.missulgan.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

import gan.missulgan.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	public String findRole(String email) {
		return memberRepository.findByAccountEmail(email)
			.map(Member::getAccountEmail)
			.orElseThrow(() -> new MemberNotFoundException(ExceptionEnum.NO_SUCH_MEMBER));
	}

	public MemberDTO findMember(String email) {
		return memberRepository.findByAccountEmail(email)
			.map(MemberDTO::from)
			.orElseThrow(() -> new MemberNotFoundException(ExceptionEnum.NO_SUCH_MEMBER));
	}

	@Transactional
	public void saveUserNickname(String email, String nickname) {
		Optional<Member> member = memberRepository.findByAccountEmail(email);
		if (member.isPresent()) {

		}
	}



}
