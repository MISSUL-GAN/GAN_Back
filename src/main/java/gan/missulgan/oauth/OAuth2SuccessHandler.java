package gan.missulgan.oauth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import gan.missulgan.auth.JwtService;
import gan.missulgan.auth.dto.JwtUserDetails;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Value("${frontend.redirectUri}")
	private String redirectionUri;
	@Value("${frontend.authorizedRedirectUris}")
	private String[] authorizedUris;

	private final JwtService jwtService;
	private final CookieOAuth2AuthorizationRequestRepository requestRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		String targetUrl = getTargetUrl(request, authentication);
		requestRepository.removeAuthorizationRequestCookies(request, response);
		response.sendRedirect(targetUrl);
	}

	private String getTargetUrl(HttpServletRequest request, Authentication authentication) {
		JwtUserDetails userDetails = (JwtUserDetails)authentication.getPrincipal();
		String accessToken = jwtService.generateAccessToken(userDetails);
		String refreshToken = jwtService.generateRefreshToken(userDetails);

		Optional<String> redirectUriOptional = requestRepository.getRedirectUriFromCookies(request)
			.map(Cookie::getValue);
		String targetUrl = selectUri(redirectUriOptional);
		return UriComponentsBuilder.fromUriString(targetUrl)
			.queryParam("accessToken", accessToken)
			.queryParam("refreshToken", refreshToken)
			.build()
			.toUriString();
	}

	private String selectUri(Optional<String> redirectUriOptional) {
		return redirectUriOptional
			.filter(this::isAuthorizedPattern)
			.orElse(redirectionUri);
	}

	private boolean isAuthorizedPattern(String requestRedirectUri) {
		return Arrays.stream(authorizedUris)
			.anyMatch(authorizedUri -> Pattern.matches(authorizedUri, requestRedirectUri));
	}
}
