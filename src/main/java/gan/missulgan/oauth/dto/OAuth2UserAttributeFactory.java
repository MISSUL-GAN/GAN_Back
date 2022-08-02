package gan.missulgan.oauth.dto;

import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class OAuth2UserAttributeFactory {

	private static final String KAKAO_REGISTRATION_ID = "kakao";

	public static OAuth2UserAttribute of(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equals(KAKAO_REGISTRATION_ID)) {
			return new KakaoOAuth2UserAttribute(attributes);
		}
		throw new OAuth2AuthenticationException("PROVIDER_NOT_SUPPORTED: " + registrationId);
	}
}
