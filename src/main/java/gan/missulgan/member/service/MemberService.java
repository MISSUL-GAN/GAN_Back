package gan.missulgan.member.service;

import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.repository.MemberRepository;
import gan.missulgan.member.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;

import gan.missulgan.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


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

	private Member getMember(String email) {
		return memberRepository.findByAccountEmail(email)
			.orElseThrow(NoSuchElementException::new); // TODO: replace with custom exception
	}

	@Transactional
	public String saveUserNickname(String email, String userNickname) {
		return getMember(email).setUserNickname(userNickname).getUserNickname();
	}

}



