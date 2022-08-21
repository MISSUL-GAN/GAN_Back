package gan.missulgan.member.controller;

import gan.missulgan.member.domain.Member;
import gan.missulgan.member.dto.MemberResponseDTO;
import gan.missulgan.member.dto.NameDTO;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.security.auth.AuthDTO;
import gan.missulgan.security.auth.dto.AuthMemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("member")
@Api(tags = "\uD83D\uDC27 사용자 정보 API")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("me")
    @ApiOperation(value = "유저 정보 가져오기", notes = "현재 인증된 유저 정보 가져옴")
    public MemberResponseDTO getMember(@AuthDTO AuthMemberDTO memberDTO) {
        Member member = memberService.getMember(memberDTO.getId());
        return MemberResponseDTO.from(member);
    }

    @GetMapping("name")
    @ApiOperation(value = "별명 가져오기")
    public NameDTO getName(@AuthDTO AuthMemberDTO memberDTO) {
        String nickname = memberService.getName(memberDTO.getId());
        return new NameDTO(nickname);
    }

    @PutMapping("name")
    @ApiOperation(value = "별명 변경하기")
    public NameDTO putName(@AuthDTO AuthMemberDTO memberDTO, @RequestBody NameDTO nameDTO) {
        String name = memberService.saveName(memberDTO.getId(), nameDTO.getName());
        return new NameDTO(name);
    }
}
