package gan.missulgan.oauth.dto;

import java.util.Map;

import gan.missulgan.member.domain.Member;
import gan.missulgan.member.domain.Role;

public class KakaoOAuth2UserAttribute extends OAuth2UserAttribute {

	private static final String KAKAO_PROVIDER_ID = "kakao";
	private static final String KAKAO_ACCOUNT_KEY = "kakao_account";
	private static final String KAKAO_PROFILE_KEY = "profile";

	private final Map<String, Object> kakaoAccount;
	private final Map<String, Object> profile;

	public KakaoOAuth2UserAttribute(Map<String, Object> attributes) {
		super(attributes);
		this.kakaoAccount = (Map<String, Object>)attributes.get(KAKAO_ACCOUNT_KEY);
		this.profile = (Map<String, Object>)kakaoAccount.get(KAKAO_PROFILE_KEY); // TODO: null 처리
	}

	@Override
	public Member toEntity() {
		return Member.builder()
			.role(Role.USER)
			.provider(KAKAO_PROVIDER_ID)
			.profileImage(getProfileImage())
			.profileNickname(getNickname())
			.userNickname(getNickname())
			.accountEmail(getEmail())
			.build();
	}

	@Override
	public String getEmail() {
		return kakaoAccount.get("email").toString();
	}

	@Override
	public String getNickname() {
		return profile.get("nickname").toString();
	}

	@Override
	public String getProfileImage() {
		return profile.get("thumbnail_image_url").toString();
	}
}
