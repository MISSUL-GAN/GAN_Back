package gan.missulgan.security.oauth.dto;

import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class OAuth2UserAttributeFactory {

	private static final String KAKAO_REGISTRATION_ID = "kakao";
	private static final String GOOGLE_REGISTRATION_ID = "google";

	public static OAuth2UserAttribute of(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equals(KAKAO_REGISTRATION_ID)) {
			return new KakaoOAuth2UserAttribute(attributes);
		}
		if (registrationId.equals(GOOGLE_REGISTRATION_ID)) {
			return new GoogleOAuth2UserAttribute(attributes);
		}
		throw new OAuth2AuthenticationException("PROVIDER_NOT_SUPPORTED: " + registrationId);
	}
}