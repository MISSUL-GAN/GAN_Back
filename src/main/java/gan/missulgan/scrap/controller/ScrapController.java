package gan.missulgan.scrap.controller;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.service.DrawingService;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.dto.MemberResponseDTO;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.scrap.service.ScrapService;
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
@RequestMapping("scrap")
@Api(tags = "\uD83D\uDCF0 스크랩 API")
public class ScrapController {

    private final ScrapService scrapService;
    private final MemberService memberService;
    private final DrawingService drawingService;

    @PostMapping("{drawingId}")
    @ResponseStatus(HttpStatus.CREATED)
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

    @GetMapping("{drawingID}")
    @ApiOperation(value = "스크랩 누른 사용자 정보 가져오기", notes = "특정 그림의 스크랩을 누른 사용자의 정보를 가져옴. 최신순 정렬 방식")
    public List<MemberResponseDTO> getScrappedMembers(@PathVariable("drawingID") Long drawingID, @PageableDefault Pageable pageable) {
        Drawing drawing = drawingService.getDrawingById(drawingID);
        return scrapService.findScrappedMembers(drawing, pageable);
    }

    @GetMapping("")
    @ApiOperation(value = "스크랩한 그림 모두 가져오기", notes = "해당 사용자가 누른 스크랩한 그림 정보를 가져옴. 최신순 정렬 방식")
    public List<DrawingResponseDTO> getScrappedDrawings(@AuthDTO AuthMemberDTO memberDTO, @PageableDefault Pageable pageable) {
        Member member = memberService.getMember(memberDTO.getId());
        return scrapService.findScrappedDrawings(member, pageable);
    }
}
