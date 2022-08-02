package gan.missulgan.oauth.dto;

import static lombok.AccessLevel.*;

import gan.missulgan.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class MemberInfoDTO {

	private final String email;
	private final String role;

	public static MemberInfoDTO from(Member member) {
		return MemberInfoDTO.builder()
			.email(member.getAccountEmail())
			.role(member.getRole().getValue())
			.build();
	}
}
