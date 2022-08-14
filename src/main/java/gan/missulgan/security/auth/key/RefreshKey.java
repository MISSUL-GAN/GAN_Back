package gan.missulgan.security.auth.key;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Qualifier("refreshKey")
@Slf4j
public class RefreshKey implements JwtKey {

    @Value("${app.auth.refreshTokenExpiry}")
    private Long refreshTokenDuration;
    @Value("${app.auth.refreshTokenNearExpiryCriterion}")
    private Long refreshTokenExpiryCriterion;

    private final Key key;
    private final JwtParser parser;

    private RefreshKey(@Value("${app.auth.refreshTokenSecret}") String refreshTokenSecret) {
        this.key = createKey(refreshTokenSecret);
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
                .setExpiration(new Date(now.getTime() + refreshTokenDuration))
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
