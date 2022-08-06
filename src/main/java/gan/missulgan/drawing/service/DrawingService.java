package gan.missulgan.drawing.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.tag.domain.DrawingTag;
import gan.missulgan.tag.repository.DrawingTagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrawingService {

	final DrawingTagRepository drawingTagRepository;

	@Transactional
	public List<DrawingResponseDTO> getDrawingsByTags(Set<Long> tagIds, Pageable pageable) {
		return drawingTagRepository.findAllById(tagIds, pageable)
			.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}
}
