package gan.missulgan.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenRequestDto {

	private String refreshToken;
}