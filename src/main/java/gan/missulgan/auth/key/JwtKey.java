package gan.missulgan.auth.key;

import io.jsonwebtoken.Claims;

public interface JwtKey {

	String getTokenWith(Claims claims);

	Claims parse(String token);

	boolean validate(String token);
}
