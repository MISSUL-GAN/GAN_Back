package gan.missulgan.security.oauth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

	@Value("${frontend.redirectUri}")
	private String redirectionUri;
	@Value("${frontend.authorizedRedirectUris}")
	private String[] authorizedUris;
	private final CookieOAuth2AuthorizationRequestRepository requestRepository;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		String targetUrl = getTargetUrl(request);
		requestRepository.removeAuthorizationRequestCookies(request, response);
		response.sendRedirect(targetUrl);
	}

	private String getTargetUrl(HttpServletRequest request) {
		Optional<String> redirectUriOptional = requestRepository.getRedirectUriFromCookies(request)
			.map(Cookie::getValue);
		String targetUrl = selectUri(redirectUriOptional);
		return UriComponentsBuilder.fromUriString(targetUrl)
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
