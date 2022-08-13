package gan.missulgan.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gan.missulgan.member.domain.Member;
import gan.missulgan.member.dto.MemberResponseDTO;
import gan.missulgan.member.dto.UserNicknameDTO;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.security.auth.AuthDTO;
import gan.missulgan.security.auth.dto.AuthMemberDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("me")
	@ApiOperation(value = "유저 정보 가져오기", notes = "현재 인증된 유저 정보 가져옴")
	public MemberResponseDTO getMember(@AuthDTO AuthMemberDTO memberDTO) {
		Member member = memberService.getMember(memberDTO.getId());
		return MemberResponseDTO.from(member);
	}

	@GetMapping("nickname")
	@ApiOperation(value = "별명 변경 전 카카오톡 이름 보여주기")
	public UserNicknameDTO getUserNickname(@AuthDTO AuthMemberDTO memberDTO) {
		String nickname = memberService.getUserNickname(memberDTO.getId());
		return new UserNicknameDTO(nickname);
	}

	@PutMapping("nickname")
	@ApiOperation(value = "별명 변경하기")
	public UserNicknameDTO putUserNickname(@AuthDTO AuthMemberDTO memberDTO,
		@RequestBody UserNicknameDTO userNicknameDTO) {
		String userNickname = memberService.saveUserNickname(memberDTO.getId(), userNicknameDTO.getUserNickname());
		return new UserNicknameDTO(userNickname);
	}
}
