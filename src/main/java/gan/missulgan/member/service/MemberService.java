package gan.missulgan.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gan.missulgan.member.domain.Member;
import gan.missulgan.member.exception.BadMemberException;
import gan.missulgan.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	public Member getMember(String email) {
		return memberRepository.findByAccountEmail(email)
			.orElseThrow(BadMemberException::new);
	}

	public Member getMember(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(BadMemberException::new);
	}

	public String getUserNickname(Long id) {
		return getMember(id).getUserNickname();
	}

	@Transactional
	public String saveUserNickname(Long id, String userNickname) {
		return getMember(id)
			.setUserNickname(userNickname)
			.getUserNickname();
	}
}



