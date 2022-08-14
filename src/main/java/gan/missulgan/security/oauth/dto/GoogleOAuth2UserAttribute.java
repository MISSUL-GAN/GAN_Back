package gan.missulgan.security.oauth.dto;

import java.util.Map;

import gan.missulgan.member.domain.Member;
import gan.missulgan.member.domain.Role;

public class GoogleOAuth2UserAttribute extends OAuth2UserAttribute {

	private static final String GOOGLE_PROVIDER_ID = "google";

	public GoogleOAuth2UserAttribute(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public Member toEntity() {
		return Member.builder()
			.role(Role.USER)
			.provider(GOOGLE_PROVIDER_ID)
			.profileImage(getProfileImage())
			.name(getNickname())
			.accountEmail(getEmail())
			.build();
	}

	@Override
	public String getEmail() {
		return getAttributes().get("email").toString();
	}

	@Override
	public String getNickname() {
		return getAttributes().get("name").toString();
	}

	@Override
	public String getProfileImage() {
		return getAttributes().get("picture").toString();
	}
}
