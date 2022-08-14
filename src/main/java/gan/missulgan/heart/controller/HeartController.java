package gan.missulgan.heart.controller;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.service.DrawingService;
import gan.missulgan.heart.service.HeartService;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.dto.MemberResponseDTO;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.security.auth.AuthDTO;
import gan.missulgan.security.auth.dto.AuthMemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("heart")
@Api(tags = "❤️ 좋아요 API")
public class HeartController {
    private final HeartService heartService;
    private final MemberService memberService;
    private final DrawingService drawingService;

    @PostMapping("{drawingID}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "좋아요 누르기", notes = "사용자가 그림의 좋아요를 누름")
    public void heart(@AuthDTO AuthMemberDTO memberDTO, @PathVariable("drawingID") Long drawingID) {
        Member member = memberService.getMember(memberDTO.getId());
        Drawing drawing = drawingService.getDrawingById(drawingID);
        heartService.heart(member, drawing);
    }

    @DeleteMapping("{drawingID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "좋아요 취소하기", notes = "사용자가 그림의 좋아요를 취소함")
    public void unHeart(@AuthDTO AuthMemberDTO memberDTO, @PathVariable("drawingID") Long drawingID) {
        Member member = memberService.getMember(memberDTO.getId());
        Drawing drawing = drawingService.getDrawingById(drawingID);
        heartService.unHeart(member, drawing);
    }

    @GetMapping("{drawingID}")
    @ApiOperation(value = "좋아요 누른 사용자 정보 가져오기", notes = "특정 그림의 좋아요를 누른 사용자의 정보를 가져옴")
    public List<MemberResponseDTO> getHeartMembers(@PathVariable("drawingID") Long drawingID, @PageableDefault Pageable pageable) {
        Drawing drawing = drawingService.getDrawingById(drawingID);
        return heartService.findHearts(drawing, pageable);
    }
}
