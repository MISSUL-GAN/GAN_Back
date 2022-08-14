package gan.missulgan.drawing.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gan.missulgan.common.exception.ForbiddenException;
import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.exception.BadDrawingException;
import gan.missulgan.drawing.repository.DrawingRepository;
import gan.missulgan.image.domain.Image;
import gan.missulgan.member.domain.Member;
import gan.missulgan.tag.domain.Tag;
import gan.missulgan.tag.repository.DrawingTagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrawingService {

	private final DrawingTagRepository drawingTagRepository;
	private final DrawingRepository drawingRepository;

	public Drawing getDrawingById(Long drawingId) {
		return drawingRepository.findById(drawingId)
			.orElseThrow(BadDrawingException::new);
	}

	@Transactional
	public List<DrawingResponseDTO> getDrawingsByRandomOrder(Set<Tag> tags, Pageable pageable) {
		return drawingTagRepository.findAllByOrTagsRandom(tags, pageable)
			.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public List<DrawingResponseDTO> getDrawingsByHeartOrder(Set<Tag> tags, Pageable pageable) {
		return drawingTagRepository.findAllByOrTags(tags, pageable)
			.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public DrawingResponseDTO addDrawing(Member member, String title, String description, Image image, Set<Tag> tags) {
		Drawing drawing = Drawing.builder()
			.title(title)
			.description(description)
			.image(image)
			.member(member)
			.build();
		drawing.setTags(tags);
		Drawing saved = drawingRepository.save(drawing);
		return DrawingResponseDTO.from(saved);
	}

	@Transactional
	public List<DrawingResponseDTO> getDrawings(Member member, Pageable pageable) {
		List<Drawing> drawings = drawingRepository.findAllByMember(member, pageable);
		return drawings.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public List<DrawingResponseDTO> getRandomDrawings(Integer size) {
		List<Drawing> drawings = drawingRepository.findAllByRandom(size);
		return drawings.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public void removeDrawing(Member member, Long drawingId) {
		Drawing drawing = getDrawingById(drawingId);
		Member drawingOwner = drawing.getMember();
		if (!drawingOwner.equals(member))
			throw new ForbiddenException("본인 작품만 삭제 가능합니다.");
		drawingRepository.delete(drawing);
	}
}
