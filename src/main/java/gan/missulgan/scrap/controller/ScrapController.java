package gan.missulgan.scrap.controller;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.service.DrawingService;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.scrap.dto.ScrapCountingResponseDTO;
import gan.missulgan.scrap.service.ScrapService;
import gan.missulgan.security.auth.AuthDTO;
import gan.missulgan.security.auth.dto.AuthMemberDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("scrap")
public class ScrapController {

    private final ScrapService scrapService;
    private final MemberService memberService;
    private final DrawingService drawingService;

    @GetMapping("")
    @ApiOperation(value = "스크랩 수 가져오기", notes = "해당 사용자의 스크랩 갯수 정보 가져옴")
    public ScrapCountingResponseDTO getHeartCounting(@AuthDTO AuthMemberDTO memberDTO) {
        Member member = memberService.getMember(memberDTO.getId());
        return new ScrapCountingResponseDTO(scrapService.getScrapCounting(member));
    }

    @PostMapping("{drawingId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "스크랩 누르기", notes = "사용자가 그림의 스크랩을 누름")
    public void scrap(@AuthDTO AuthMemberDTO memberDTO, @PathVariable("drawingId") Long drawingID) {
        Member member = memberService.getMember(memberDTO.getId());
        Drawing drawing = drawingService.getDrawingById(drawingID);
        scrapService.scrap(member, drawing);
    }

    @DeleteMapping("{drawingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "스크랩 취소하기", notes = "사용자가 그림의 스크랩을 취소함")
    public void unScrap(@AuthDTO AuthMemberDTO memberDTO, @PathVariable("drawingId") Long drawingID) {
        Member member = memberService.getMember(memberDTO.getId());
        Drawing drawing = drawingService.getDrawingById(drawingID);
        scrapService.unScrap(member, drawing);
    }

    @GetMapping("drawings")
    @ApiOperation(value = "스크랩한 그림 모두 가져오기", notes = "해당 사용자가 누른 스크랩한 그림 정보를 가져옴")
    public List<DrawingResponseDTO> getScraps(@AuthDTO AuthMemberDTO memberDTO, @PageableDefault Pageable pageable) {
        Member member = memberService.getMember(memberDTO.getId());
        return scrapService.findScraps(member, pageable);
    }
}
