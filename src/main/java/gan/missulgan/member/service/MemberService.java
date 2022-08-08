package gan.missulgan.member.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gan.missulgan.member.domain.Member;
import gan.missulgan.member.dto.MemberDTO;
import gan.missulgan.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	public String findRole(String email) {
		return memberRepository.findByAccountEmail(email)
			.map(Member::getAccountEmail)
			.orElseThrow(NoSuchElementException::new);
	}

	public MemberDTO findMember(String email) {
		return memberRepository.findByAccountEmail(email)
			.map(MemberDTO::from)
			.orElseThrow(NoSuchElementException::new);
	}

	public Member getMember(String email) {
		return memberRepository.findByAccountEmail(email)
			.orElseThrow(NoSuchElementException::new); // TODO: replace with custom exception
	}

	public Member getMember(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(NoSuchElementException::new); // TODO: replace with custom exception
	}

	@Transactional
	public String saveUserNickname(String email, String userNickname) {
		return getMember(email).setUserNickname(userNickname).getUserNickname();
	}

}



