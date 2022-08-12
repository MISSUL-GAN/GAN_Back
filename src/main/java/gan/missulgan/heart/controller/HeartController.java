package gan.missulgan.heart.controller;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.service.DrawingService;
import gan.missulgan.heart.dto.DrawingIdRequestDTO;
import gan.missulgan.heart.dto.HeartCountingResponseDTO;
import gan.missulgan.member.domain.Member;
import gan.missulgan.heart.service.HeartService;
import gan.missulgan.member.dto.MemberDTO;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.security.auth.AuthDTO;
import gan.missulgan.security.auth.dto.AuthMemberDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("heart")
public class HeartController {
    private final HeartService heartService;
    private final MemberService memberService;
    private final DrawingService drawingService;


    @GetMapping("")
    @ApiOperation(value = "좋아요 수 가져오기", notes = "특정 그림의 좋아요 수 정보 가져옴")
    public HeartCountingResponseDTO getHeartCounting(DrawingIdRequestDTO drawingIdRequestDTO) {
        Drawing drawing = drawingService.getDrawingById(drawingIdRequestDTO.getDrawingId());
        return new HeartCountingResponseDTO(heartService.getHeartCounting(drawing));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "좋아요 누르기", notes = "사용자가 그림의 좋아요를 누름")
    public void heart(@AuthDTO AuthMemberDTO memberDTO, DrawingIdRequestDTO drawingIdRequestDTO) {
        Member member = memberService.getMember(memberDTO.getId());
        Drawing drawing = drawingService.getDrawingById(drawingIdRequestDTO.getDrawingId());
        heartService.heart(member, drawing);
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "좋아요 취소하기", notes = "사용자가 그림의 좋아요를 취소함")
    public void unHeart(@AuthDTO AuthMemberDTO memberDTO, DrawingIdRequestDTO drawingIdRequestDTO) {
        Member member = memberService.getMember(memberDTO.getId());
        Drawing drawing = drawingService.getDrawingById(drawingIdRequestDTO.getDrawingId());
        heartService.unHeart(member, drawing);
    }

    @GetMapping("members")
    @ApiOperation(value = "좋아요 누른 사용자 정보 가져오기", notes = "특정 그림의 좋아요를 누른 사용자의 정보를 가져옴")
    public List<MemberDTO> getHeartMembers(DrawingIdRequestDTO drawingIdRequestDTO, @PageableDefault Pageable pageable) {
        Drawing drawing = drawingService.getDrawingById(drawingIdRequestDTO.getDrawingId());
        return heartService.getHearts(drawing, pageable);
    }
}
