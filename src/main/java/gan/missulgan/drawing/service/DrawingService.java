package gan.missulgan.drawing.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.exception.BadDrawingException;
import gan.missulgan.drawing.exception.DrawingOwnerException;
import gan.missulgan.drawing.repository.DrawingRepository;
import gan.missulgan.image.domain.Image;
import gan.missulgan.member.domain.Member;
import gan.missulgan.nft.domain.Nft;
import gan.missulgan.nft.repository.NftRepository;
import gan.missulgan.tag.domain.Tag;
import gan.missulgan.tag.repository.DrawingTagRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrawingService {

	private final DrawingTagRepository drawingTagRepository;
	private final DrawingRepository drawingRepository;
	private final NftRepository nftRepository;

	public Drawing getDrawingById(Long drawingId) {
		return drawingRepository.findById(drawingId)
			.orElseThrow(BadDrawingException::new);
	}

	@Transactional
	public List<DrawingResponseDTO> getDrawingsByRandom(Set<Tag> tags, Pageable pageable) {
		return drawingTagRepository.findAllByOrTagsRandom(tags, pageable)
			.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public List<DrawingResponseDTO> getDrawingsByRandom(Pageable pageable) {
		List<Drawing> drawings = drawingRepository.findAllByRandom(pageable);
		return drawings.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public List<DrawingResponseDTO> getDrawingsByHeartCountOrder(Set<Tag> tags, Pageable pageable) {
		return drawingTagRepository.findAllByOrTagsOrderByHeartCount(tags, pageable)
			.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public List<DrawingResponseDTO> getDrawingsByHeartCountOrder(Pageable pageable) {
		return drawingRepository.findAllOrderByHeartCount(pageable)
			.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public List<DrawingResponseDTO> getDrawings(Member member, Pageable pageable) {
		List<Drawing> drawings = drawingRepository.findAllByMember(member, pageable);
		return drawings.stream()
			.map(DrawingResponseDTO::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public DrawingResponseDTO addDrawing(Member member, String title, String description, Image image, Set<Tag> tags,
		Optional<Nft> nftOptional) {
		Drawing.DrawingBuilder drawingBuilder = Drawing.builder();
		nftOptional.ifPresent(drawingBuilder::nft);
		Drawing drawing = drawingBuilder
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
	public DrawingResponseDTO putNft(Member member, Long drawingId, Nft nft) {
		Drawing drawing = getDrawingById(drawingId);
		validateDrawingOwner(member, drawing);
		Nft savedNft = nftRepository.save(nft);
		drawing.putNftInfo(savedNft);
		return DrawingResponseDTO.from(drawing);
	}

	@Transactional
	public void removeDrawing(Member member, Long drawingId) {
		Drawing drawing = getDrawingById(drawingId);
		validateDrawingOwner(member, drawing);
		drawingRepository.delete(drawing);
	}

	private void validateDrawingOwner(Member member, Drawing drawing) {
		Member drawingOwner = drawing.getMember();
		if (!drawingOwner.equals(member))
			throw new DrawingOwnerException();
	}
}
