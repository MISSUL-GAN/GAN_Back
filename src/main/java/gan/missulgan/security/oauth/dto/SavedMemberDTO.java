package gan.missulgan.security.oauth.dto;

import static lombok.AccessLevel.*;

import gan.missulgan.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class SavedMemberDTO {

	private final Long id;
	private final String email;
	private final String role;
	private final String nickname;
	private final Boolean isFirstTime;


	public static SavedMemberDTO from(Member member) {
		return SavedMemberDTO.builder()
			.id(member.getId())
			.email(member.getAccountEmail())
			.role(member.getRole().getValue())
			.nickname(member.getUserNickname())
			.isFirstTime(false)
			.build();
	}

	public static SavedMemberDTO from(Member member, Boolean isFirstTime) {
		return SavedMemberDTO.builder()
			.id(member.getId())
			.email(member.getAccountEmail())
			.role(member.getRole().getValue())
			.nickname(member.getUserNickname())
			.isFirstTime(isFirstTime)
			.build();
	}
}
