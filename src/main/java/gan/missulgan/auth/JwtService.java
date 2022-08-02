package gan.missulgan.auth;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import gan.missulgan.auth.dto.JwtUserDetails;
import gan.missulgan.auth.dto.TokenResponseDto;
import gan.missulgan.auth.key.JwtKey;
import gan.missulgan.member.domain.MemberService;
import gan.missulgan.oauth.dto.MemberInfoDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {

	private static final String ROLE_KEY = "role";

	private final MemberService memberService;
	private final JwtKey accessKey;
	private final JwtKey refreshKey;

	public String generateAccessToken(JwtUserDetails userDetails) {
		Claims claims = Jwts.claims().setSubject(userDetails.getName());
		claims.put(ROLE_KEY, userDetails.getRole());
		return accessKey.getTokenWith(claims);
	}

	public String generateRefreshToken(JwtUserDetails userDetails) {
		Claims claims = Jwts.claims().setSubject(userDetails.getName());
		return refreshKey.getTokenWith(claims);
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = accessKey.parse(accessToken);
		String email = claims.getSubject();
		String role = claims.get(ROLE_KEY, String.class);

		MemberInfoDTO memberInfoDTO = MemberInfoDTO.builder()
			.email(email)
			.role(role)
			.build();
		Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(role));
		return new UsernamePasswordAuthenticationToken(memberInfoDTO, "", authorities);
	}

	public TokenResponseDto renew(String refreshToken) {
		Claims claims = refreshKey.parse(refreshToken);
		String email = claims.getSubject();
		String role = memberService.findRole(email);
		claims.put(ROLE_KEY, role);
		String accessToken = accessKey.getTokenWith(claims);
		return new TokenResponseDto(accessToken);
	}

	public boolean validate(String accessToken) {
		return accessKey.validate(accessToken);
	}
}
