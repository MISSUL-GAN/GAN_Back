package gan.missulgan.member.dto;

import static lombok.AccessLevel.*;

import gan.missulgan.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class MemberResponseDTO {

	private final Long id;
	private final String userNickname;
	private final String profileImage;
	private final String accountEmail;

	public static MemberResponseDTO from(Member member) {
		return MemberResponseDTO.builder()
			.id(member.getId())
			.userNickname(member.getUserNickname())
			.accountEmail(member.getAccountEmail())
			.profileImage(member.getProfileImage())
			.build();
	}
}
