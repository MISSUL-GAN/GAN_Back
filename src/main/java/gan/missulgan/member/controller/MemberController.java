package gan.missulgan.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gan.missulgan.auth.AuthenticatedEmail;
import gan.missulgan.member.domain.MemberService;
import gan.missulgan.member.dto.MemberDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("me")
	@ApiOperation(value = "유저 정보 가져오기", notes = "현재 인증된 유저 정보 가져옴")
	public MemberDTO getMember(@AuthenticatedEmail String email) {
		return memberService.findUser(email);
	}
}
