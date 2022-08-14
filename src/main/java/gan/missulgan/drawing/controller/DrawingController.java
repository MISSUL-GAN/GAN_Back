package gan.missulgan.drawing.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import gan.missulgan.drawing.dto.DrawingAddRequestDTO;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.dto.NftAddRequestDTO;
import gan.missulgan.drawing.dto.TagDrawingSearchRequestDTO;
import gan.missulgan.drawing.service.DrawingService;
import gan.missulgan.image.domain.Image;
import gan.missulgan.image.domain.ImageService;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.nft.domain.Nft;
import gan.missulgan.security.auth.AuthDTO;
import gan.missulgan.security.auth.dto.AuthMemberDTO;
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
	private final ImageService imageService;

	@PostMapping("tags/random")
	@ApiOperation(value = "태그 필터링 + 랜덤 순", notes = "태그로 그림 필터링. `tagId` 필요, 랜덤 순으로 나옴.  **페이징** 가능, `AccessToken` 불필요")
	public List<DrawingResponseDTO> getDrawingsByRandomOrder(
		@Valid @RequestBody TagDrawingSearchRequestDTO tagDrawingSearchRequestDTO, @PageableDefault Pageable pageable) {
		Set<Long> tagIds = tagDrawingSearchRequestDTO.getTagIds();
		Set<Tag> tags = tagService.getTagsByIds(tagIds);
		return drawingService.getDrawingsByRandomOrder(tags, pageable);
	}

	@PostMapping("tags/heart")
	@ApiOperation(value = "태그 필터링 + 좋아요 순", notes = "태그로 그림 필터링. `tagId` 필요, 좋아요 순으로 나옴.  **페이징** 가능, `AccessToken` 불필요")
	public List<DrawingResponseDTO> getDrawingsByHeartOrder(
		@Valid @RequestBody TagDrawingSearchRequestDTO tagDrawingSearchRequestDTO, @PageableDefault Pageable pageable) {
		Set<Long> tagIds = tagDrawingSearchRequestDTO.getTagIds();
		Set<Tag> tags = tagService.getTagsByIds(tagIds);
		return drawingService.getDrawingsByHeartOrder(tags, pageable);
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
	public List<DrawingResponseDTO> getDrawings(@AuthDTO AuthMemberDTO memberDTO, @PageableDefault Pageable pageable) {
		Member member = memberService.getMember(memberDTO.getId());
		return drawingService.getDrawings(member, pageable);
	}

	@PostMapping("")
	@ApiOperation(value = "그림 추가", notes = "그림 추가. 태그 필요하며, `fileName`을 넣어야함<br>**NFT는 선택사항!**")
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
