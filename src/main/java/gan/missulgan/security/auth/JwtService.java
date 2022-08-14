package gan.missulgan.security.auth;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import gan.missulgan.member.repository.MemberRepository;
import gan.missulgan.security.auth.dto.AuthMemberDTO;
import gan.missulgan.security.auth.dto.TokenResponseDto;
import gan.missulgan.security.auth.key.JwtKey;
import gan.missulgan.security.oauth.dto.SavedMemberDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtService {

	private static final String ROLE_KEY = "role";
	private static final String ID_KEY = "id";

	private final MemberRepository memberRepository;
	private final JwtKey accessKey;
	private final JwtKey refreshKey;

	public String generateAccessToken(SavedMemberDTO savedMemberDTO) {
		Claims claims = buildClaims(savedMemberDTO);
		return accessKey.getTokenWith(claims);
	}

	public String generateRefreshToken(SavedMemberDTO savedMemberDTO) {
		Claims claims = buildClaims(savedMemberDTO);
		return refreshKey.getTokenWith(claims);
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = accessKey.parse(accessToken);
		String email = claims.getSubject();
		String id = (String)(claims.get(ID_KEY));
		String role = (String)claims.get(ROLE_KEY);
		Long idLong = getIdLong(email, id); // TODO: 쓰레기 제거

		Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(role));
		AuthMemberDTO authMemberDTO = AuthMemberDTO.builder()
			.id(idLong)
			.email(email)
			.role(role)
			.build();
		return new UsernamePasswordAuthenticationToken(authMemberDTO, "", authorities);
	}

	@Deprecated
	private Long getIdLong(String email, String id) {
		Long idLong;
		if (id == null) {
			idLong = getMember(email).getId();
		} else {
			idLong = Long.parseLong(id);
		}
		return idLong;
	}

	public TokenResponseDto renew(String refreshToken) {
		Claims claims = refreshKey.parse(refreshToken);
		String email = claims.getSubject();
		SavedMemberDTO savedMemberDTO = getMember(email);
		String accessToken = generateAccessToken(savedMemberDTO);
		return new TokenResponseDto(accessToken);
	}

	public boolean validate(String accessToken) {
		return accessKey.validate(accessToken);
	}

	private SavedMemberDTO getMember(String email) {
		return memberRepository.findByAccountEmail(email)
			.map(SavedMemberDTO::from)
			.orElseThrow(NoSuchElementException::new);
	}

	private Claims buildClaims(SavedMemberDTO savedMemberDTO) {
		Long id = savedMemberDTO.getId();
		String email = savedMemberDTO.getEmail();
		String role = savedMemberDTO.getRole();

		Claims claims = Jwts.claims();
		claims.setSubject(email);
		claims.put(ID_KEY, id.toString());
		claims.put(ROLE_KEY, role);
		return claims;
	}
}
