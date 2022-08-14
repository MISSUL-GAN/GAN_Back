package gan.missulgan.security.oauth;

import gan.missulgan.security.auth.JwtService;
import gan.missulgan.security.auth.dto.OAuthUserImpl;
import gan.missulgan.security.oauth.dto.SavedMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

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
        OAuthUserImpl oAuthUser = (OAuthUserImpl) authentication.getPrincipal();
        SavedMemberDTO savedMemberDTO = oAuthUser.getSavedMemberDTO();
        String accessToken = jwtService.generateAccessToken(savedMemberDTO);
        String refreshToken = jwtService.generateRefreshToken(savedMemberDTO);

        Optional<String> redirectUriOptional = requestRepository.getRedirectUriFromCookies(request)
                .map(Cookie::getValue);
        String targetUrl = selectUri(redirectUriOptional);
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .queryParam("firstTime", savedMemberDTO.getIsFirstTime())
                .queryParam("name", savedMemberDTO.getName())
                .encode(StandardCharsets.UTF_8)
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
