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

	public Member getMember(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(BadMemberException::new);
	}

	public String getName(Long id) {
		return getMember(id).getName();
	}

	@Transactional
	public String saveName(Long id, String name) {
		return getMember(id)
			.updateName(name)
			.getName();
	}
}



