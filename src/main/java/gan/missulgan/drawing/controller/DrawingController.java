package gan.missulgan.drawing.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gan.missulgan.auth.AuthenticatedEmail;
import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingAddRequestDTO;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.dto.TagDrawingSearchRequestDTO;
import gan.missulgan.drawing.service.DrawingService;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.tag.domain.Tag;
import gan.missulgan.tag.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("drawing")
public class DrawingController {

	private final DrawingService drawingService;
	private final TagService tagService;
	private final MemberService memberService;

	@PostMapping("tags")
	@ApiOperation(value = "태그로 그림 가져오기", notes = "태그로 그림을 검색함. `tagId`를 입력해야하며,  **페이징** 가능, `AccessToken` 불필요")
	public List<DrawingResponseDTO> getDrawingsByTags(
		@Valid @RequestBody TagDrawingSearchRequestDTO tagDrawingSearchRequestDTO,
		@PageableDefault Pageable pageable) {
		Set<Long> tagIds = tagDrawingSearchRequestDTO.getTagIds();
		Set<Tag> tags = tagService.getTagsByIds(tagIds);
		return drawingService.getDrawingsByTags(tags, pageable);
	}

	@GetMapping("{memberId}")
	@ApiOperation(value = "특정 멤버의 그림 가져오기", notes = "특정 멤버의 그림 가져옴, **페이징** 가능, `AccessToken` 불필요")
	public List<DrawingResponseDTO> getDrawings(@PathVariable("memberId") Long memberId,
		@PageableDefault Pageable pageable) {
		Member member = memberService.getMember(memberId);
		return drawingService.getDrawings(member, pageable);
	}

	@GetMapping("random")
	@ApiOperation(value = "랜덤으로 그림 가져오기", notes = "랜덤으로 그림 가져옴, 기본값 20개, `AccessToken` 불필요")
	public List<DrawingResponseDTO> getDrawings(@RequestParam(defaultValue = "20") Integer size) {
		return drawingService.getRandomDrawings(size);
	}

	@GetMapping("")
	@ApiOperation(value = "현재 멤버의 그림 가져오기", notes = "현재 멤버의 그림 가져옴")
	public List<DrawingResponseDTO> getDrawings(@AuthenticatedEmail String email, @PageableDefault Pageable pageable) {
		Member member = memberService.getMember(email);
		return drawingService.getDrawings(member, pageable);
	}

	@PostMapping("")
	@ApiOperation(value = "그림 추가", notes = "그림 추가. 태그 필요하며, `fileName`을 넣어야함")
	public DrawingResponseDTO addDrawing(@AuthenticatedEmail String email,
		@Valid @RequestBody DrawingAddRequestDTO requestDTO) {
		Member member = memberService.getMember(email);
		Set<Long> tagIds = requestDTO.getTagIds();
		Set<Tag> tags = tagService.getTagsByIds(tagIds);
		return drawingService.addDrawing(member, tags, requestDTO);
	}

	@DeleteMapping("{drawingId}")
	@ApiOperation(value = "그림 삭제", notes = "그림 삭제. 그림 업로더만 삭제 가능")
	public void removeDrawing(@AuthenticatedEmail String email,
		@PathVariable Long drawingId) {
		Member member = memberService.getMember(email);
		// Drawing drawing = drawingService.getDrawingById(drawingId);
		drawingService.removeDrawing(member, drawingId);
	}
}
