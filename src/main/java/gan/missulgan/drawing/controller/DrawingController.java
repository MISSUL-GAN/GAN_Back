package gan.missulgan.drawing.controller;

import gan.missulgan.drawing.dto.DrawingAddRequestDTO;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.dto.NftAddRequestDTO;
import gan.missulgan.drawing.dto.TagDrawingSearchRequestDTO;
import gan.missulgan.drawing.service.DrawingService;
import gan.missulgan.image.domain.Image;
import gan.missulgan.image.service.ImageService;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.nft.domain.Nft;
import gan.missulgan.security.auth.AuthDTO;
import gan.missulgan.security.auth.dto.AuthMemberDTO;
import gan.missulgan.tag.domain.Tag;
import gan.missulgan.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("drawing")
@Api(tags = "🖼 그림 API")
public class DrawingController {

    private final DrawingService drawingService;
    private final TagService tagService;
    private final MemberService memberService;
    private final ImageService imageService;

    @PostMapping("random/tags")
    @ApiOperation(value = "랜덤으로 그림 가져오기 + 태그 🔒❌", notes = "태그로 그림 필터링. `tagId` 필요, 랜덤 순으로 나옴. **페이징** 가능")
    public List<DrawingResponseDTO> getDrawingsByRandom(
            @Valid @RequestBody TagDrawingSearchRequestDTO tagDrawingSearchRequestDTO, @PageableDefault Pageable pageable) {
        Set<Long> tagIds = tagDrawingSearchRequestDTO.getTagIds();
        Set<Tag> tags = tagService.getTagsByIds(tagIds);
        return drawingService.getDrawingsByRandom(tags, pageable);
    }

    @GetMapping("random")
    @ApiOperation(value = "랜덤으로 그림 가져오기 🔒❌", notes = "랜덤으로 그림 가져옴, **페이징 가능**")
    public List<DrawingResponseDTO> getDrawingsByRandom(@PageableDefault Pageable pageable) {
        return drawingService.getDrawingsByRandom(pageable);
    }

    @PostMapping("heart/tags")
    @ApiOperation(value = "좋아요 순으로 그림 가져오기 + 태그 🔒❌", notes = "태그로 그림 필터링. `tagId` 필요, 좋아요 순으로 나옴. **페이징** 가능")
    public List<DrawingResponseDTO> getDrawingsByHeartOrder(
            @Valid @RequestBody TagDrawingSearchRequestDTO tagDrawingSearchRequestDTO, @PageableDefault Pageable pageable) {
        Set<Long> tagIds = tagDrawingSearchRequestDTO.getTagIds();
        Set<Tag> tags = tagService.getTagsByIds(tagIds);
        return drawingService.getDrawingsByHeartCountOrder(tags, pageable);
    }

    @GetMapping("heart")
    @ApiOperation(value = "좋아요 순으로 그림 가져오기 🔒❌", notes = "좋아요 순으로 가져오기. **페이징** 가능")
    public List<DrawingResponseDTO> getDrawingsByHeartOrder(@PageableDefault Pageable pageable) {
        return drawingService.getDrawingsByHeartCountOrder(pageable);
    }

    @GetMapping("member/{memberId}")
    @ApiOperation(value = "특정 멤버의 그림 가져오기 🔒❌", notes = "특정 멤버의 그림 가져오기, **페이징** 가능")
    public List<DrawingResponseDTO> getDrawings(@PathVariable("memberId") Long memberId,
                                                @PageableDefault Pageable pageable) {
        Member member = memberService.getMember(memberId);
        return drawingService.getDrawings(member, pageable);
    }

    @GetMapping("{drawingId}")
    @ApiOperation(value = "그림 가져오기 🔒❌", notes = "특정 그림 가져옴")
    public DrawingResponseDTO getDrawing(@PathVariable Long drawingId) {
        return DrawingResponseDTO.from(drawingService.getDrawingById(drawingId));
    }

    @GetMapping("")
    @ApiOperation(value = "현재 멤버의 그림 가져오기", notes = "현재 멤버의 그림 가져옴")
    public List<DrawingResponseDTO> getDrawings(@AuthDTO AuthMemberDTO memberDTO, @PageableDefault Pageable pageable) {
        Member member = memberService.getMember(memberDTO.getId());
        return drawingService.getDrawings(member, pageable);
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    @ApiOperation(value = "그림 추가", notes = "그림 추가. 태그 필요하며, `fileName`을 넣어야 함<br>**NFT 정보는 선택사항!**<br><h2>`fileName`은 이미지 서버가 줌!</h2>")
    public DrawingResponseDTO addDrawing(@AuthDTO AuthMemberDTO memberDTO,
                                         @Valid @RequestBody DrawingAddRequestDTO requestDTO) {
        Member member = memberService.getMember(memberDTO.getId());
        Set<Long> tagIds = requestDTO.getTagIds();
        Set<Tag> tags = tagService.getTagsByIds(tagIds);
        Image image = imageService.getImage(requestDTO.getFileName());
        Optional<Nft> nftOptional = requestDTO.getNft();

        String title = requestDTO.getTitle();
        String description = requestDTO.getDescription();
        return drawingService.addDrawing(member, title, description, image, tags, nftOptional);
    }

    @PutMapping("{drawingId}/nft")
    @ApiOperation(value = "NFT 정보 넣기", notes = "본인 그림에만 가능")
    @ResponseStatus(NO_CONTENT)
    public DrawingResponseDTO addNft(@AuthDTO AuthMemberDTO memberDTO, @PathVariable Long drawingId,
                                     @Valid @RequestBody NftAddRequestDTO requestDTO) {
        Member member = memberService.getMember(memberDTO.getId());
        Nft nft = requestDTO.toEntity();
        return drawingService.putNft(member, drawingId, nft);
    }

    @DeleteMapping("{drawingId}")
    @ApiOperation(value = "그림 삭제", notes = "그림 삭제. 그림 업로더만 삭제 가능")
    @ResponseStatus(NO_CONTENT)
    public void removeDrawing(@AuthDTO AuthMemberDTO memberDTO, @PathVariable Long drawingId) {
        Member member = memberService.getMember(memberDTO.getId());
        drawingService.removeDrawing(member, drawingId);
    }
}
