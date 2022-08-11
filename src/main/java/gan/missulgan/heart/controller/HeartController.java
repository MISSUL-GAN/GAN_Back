package gan.missulgan.heart.controller;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.service.DrawingService;
import gan.missulgan.heart.dto.DrawingIdRequestDTO;
import gan.missulgan.heart.dto.HeartCountingResponseDTO;
import gan.missulgan.heart.dto.HeartRequestDTO;
import gan.missulgan.member.domain.Member;
import gan.missulgan.heart.service.HeartService;
import gan.missulgan.member.dto.MemberDTO;
import gan.missulgan.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return heartService.getHeartCounting(drawing);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "좋아요 누르기", notes = "사용자가 그림의 좋아요를 누름")
    public void heart(HeartRequestDTO heartRequestDTO) {
        Member member = memberService.getMember(heartRequestDTO.getMemberId());
        Drawing drawing = drawingService.getDrawingById(heartRequestDTO.getDrawingId());
        heartService.heart(member, drawing);
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "좋아요 취소하기", notes = "사용자가 그림의 좋아요를 취소함")
    public void unHeart(HeartRequestDTO heartRequestDTO) {
        Member member = memberService.getMember(heartRequestDTO.getMemberId());
        Drawing drawing = drawingService.getDrawingById(heartRequestDTO.getDrawingId());
        heartService.unHeart(member, drawing);
    }

    @GetMapping("members")
    @ApiOperation(value = "좋아요 누른 사용자 정보 가져오기", notes = "특정 그림의 좋아요를 누른 사용자의 정보를 가져옴")
    public List<MemberDTO> getHeartMembers(DrawingIdRequestDTO drawingIdRequestDTO) {
        Drawing drawing = drawingService.getDrawingById(drawingIdRequestDTO.getDrawingId());
        return heartService.getHearts(drawing);
    }
}
