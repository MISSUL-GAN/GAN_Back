package gan.missulgan.security.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gan.missulgan.security.auth.JwtService;
import gan.missulgan.security.auth.dto.TokenResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

	private final JwtService jwtService;

	@GetMapping(path = "renew/{refreshToken}")
	@ApiOperation(value = "토큰 갱신", notes = "`refreshToken`으로 새 `accessToken`을 발급")
	public TokenResponseDto renewToken(@PathVariable String refreshToken) {
		return jwtService.renew(refreshToken);
	}
}
