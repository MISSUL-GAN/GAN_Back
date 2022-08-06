package gan.missulgan.drawing.controller;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.dto.DrawingTagSearchRequestDTO;
import gan.missulgan.drawing.service.DrawingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("drawing")
public class DrawingController {

	private final DrawingService drawingService;

	@PostMapping("search/tag")
	public List<DrawingResponseDTO> getDrawingsByTags(@RequestBody DrawingTagSearchRequestDTO searchRequestDTO,
		@PageableDefault Pageable pageable) {
		Set<Long> tagIds = searchRequestDTO.getTagIds();
		return drawingService.getDrawingsByTags(tagIds, pageable);
	}
}
