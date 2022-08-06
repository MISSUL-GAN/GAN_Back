package gan.missulgan.member.dto;

import static lombok.AccessLevel.*;

import gan.missulgan.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class MemberDTO {

	private final Long id;
	private final String profileNickname;
	private final String userNickname;
	private final String profileImage;
	private final String accountEmail;

	public static MemberDTO from(Member member) {
		return MemberDTO.builder()
			.id(member.getId())
			.userNickname(member.getUserNickname())
			.profileNickname(member.getProfileNickname())
			.accountEmail(member.getAccountEmail())
			.profileImage(member.getProfileImage())
			.build();
	}
}
