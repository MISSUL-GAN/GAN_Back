package gan.missulgan.security.auth.key;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Qualifier("accessKey")
@Slf4j
public class AccessKey implements JwtKey {

	@Value("${app.auth.accessTokenExpiry}")
	private Long accessTokenDuration;

	private final Key key;
	private final JwtParser parser;

	private AccessKey(@Value("${app.auth.accessTokenSecret}") String accessTokenSecret) {
		this.key = createKey(accessTokenSecret);
		this.parser = Jwts.parserBuilder()
			.setSigningKey(key)
			.build();
	}

	Key createKey(String secret) {
		byte[] secretBytes = Base64.getEncoder()
			.encode(secret.getBytes());
		return Keys.hmacShaKeyFor(secretBytes);
	}

	@Override
	public String getTokenWith(Claims claims) {
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenDuration))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	@Override
	public Claims parse(String token) {
		return parser.parseClaimsJws(token)
			.getBody();
	}

	@Override
	public boolean validate(String token) {
		try {
			parse(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.warn(e.getMessage());
		}
		return false;
	}
}
