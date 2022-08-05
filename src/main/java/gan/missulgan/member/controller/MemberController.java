package gan.missulgan.member.controller;

import gan.missulgan.member.dto.UserNicknameDTO.UserNicknameRequest;
import gan.missulgan.member.dto.UserNicknameDTO.UserNicknameResponse;
import gan.missulgan.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

import gan.missulgan.auth.AuthenticatedEmail;
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
		return memberService.findMember(email);
	}

	@GetMapping("nickname")
	@ApiOperation(value = "별명 변경 전 카카오톡 이름 보여주기")
	public UserNicknameResponse getUserNickname(@AuthenticatedEmail String email) {
		return new UserNicknameResponse(memberService.findMember(email).getUserNickname());
	}

	@PutMapping("nickname")
	@ApiOperation(value = "별명 변경하기")
	public UserNicknameResponse putUserNickname(@RequestBody UserNicknameRequest userNicknameRequest) {
		return new UserNicknameResponse(memberService.saveUserNickname(userNicknameRequest));
	}

}
