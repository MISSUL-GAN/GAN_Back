package gan.missulgan.drawing.service;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.exception.BadDrawingException;
import gan.missulgan.drawing.exception.DrawingOwnerException;
import gan.missulgan.drawing.repository.DrawingRepository;
import gan.missulgan.image.domain.Image;
import gan.missulgan.member.domain.Member;
import gan.missulgan.nft.domain.NFT;
import gan.missulgan.tag.domain.Tag;
import gan.missulgan.tag.repository.DrawingTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrawingService {

    private final DrawingTagRepository drawingTagRepository;
    private final DrawingRepository drawingRepository;

    public Drawing getDrawingById(Long drawingId) {
        return drawingRepository.findById(drawingId)
                .orElseThrow(BadDrawingException::new);
    }

    private DrawingResponseDTO buildDrawingResponseDTO(Drawing drawing, Optional<Long> optionalAuthMemberId) { // TODO: 제거하고 별도의 endpoint로..!
        DrawingResponseDTO drawingResponseDTO = DrawingResponseDTO.from(drawing);
        optionalAuthMemberId.ifPresent(authMemberId -> {
            drawingResponseDTO.putDidHeart(drawing.didHeart(authMemberId));
            drawingResponseDTO.putDidScrap(drawing.didScrap(authMemberId));
        });
        return drawingResponseDTO;
    }

    @Transactional
    public DrawingResponseDTO getDrawing(Long drawingId, Optional<Long> optionalAuthMemberId) {
        Drawing drawing = getDrawingById(drawingId);
        return buildDrawingResponseDTO(drawing, optionalAuthMemberId);
    }

    @Transactional
    public List<DrawingResponseDTO> getDrawingsByRandom(Set<Tag> tags, Optional<Long> optionalAuthMemberId) {
        return drawingTagRepository.findAllByOrTagsRandom(tags)
                .stream()
                .map(this::getDrawingById)
                .map(drawing -> buildDrawingResponseDTO(drawing, optionalAuthMemberId))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DrawingResponseDTO> getDrawingsByRandom(Optional<Long> optionalAuthMemberId) {
        List<Drawing> drawings = drawingRepository.findAllByRandom();
        return drawings.stream()
                .map(drawing -> buildDrawingResponseDTO(drawing, optionalAuthMemberId))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DrawingResponseDTO> getDrawingsByHeartCountOrder(Set<Tag> tags, Pageable pageable, Optional<Long> optionalAuthMemberId) {
        return drawingTagRepository.findAllByOrTagsOrderByHeartCount(tags, pageable)
                .stream()
                .map(drawing -> buildDrawingResponseDTO(drawing, optionalAuthMemberId))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DrawingResponseDTO> getDrawingsByHeartCountOrder(Pageable pageable, Optional<Long> optionalAuthMemberId) {
        return drawingRepository.findAllOrderByHeartCount(pageable)
                .stream()
                .map(drawing -> buildDrawingResponseDTO(drawing, optionalAuthMemberId))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DrawingResponseDTO> getDrawingsByRecentOrder(Set<Tag> tags, Pageable pageable, Optional<Long> optionalAuthMemberId) {
        return drawingTagRepository.findAllByOrTagsOrderByIdDesc(tags, pageable).stream()
                .map(drawing -> buildDrawingResponseDTO(drawing, optionalAuthMemberId))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DrawingResponseDTO> getDrawingsByRecentOrder(Pageable pageable, Optional<Long> optionalAuthMemberId) {
        return drawingRepository.findAllByOrderByCreatedAtDesc(pageable).stream()
                .map(drawing -> buildDrawingResponseDTO(drawing, optionalAuthMemberId))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DrawingResponseDTO> getDrawings(Member member, Pageable pageable, Optional<Long> optionalAuthMemberId) {
        List<Drawing> drawings = drawingRepository.findAllByMember(member, pageable);
        return drawings.stream()
                .map(drawing -> buildDrawingResponseDTO(drawing, optionalAuthMemberId))
                .collect(Collectors.toList());
    }

    @Transactional
    public DrawingResponseDTO addDrawing(Member member, String title, String description, Image image,
                                            Set<Tag> tags, Optional<NFT> nftOptional) {
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
    public void updateDrawing(Member member, Drawing drawing, String title, String description, Set<Tag> tags) {
        validateDrawingOwner(member, drawing);
        drawing.update(title, description, tags);
        drawingRepository.save(drawing);
    }

    @Transactional
    public void removeDrawing(Member member, Long drawingId) {
        Drawing drawing = getDrawingById(drawingId);
        validateDrawingOwner(member, drawing);
        drawingRepository.delete(drawing);
    }

    public void validateDrawingOwner(Member member, Drawing drawing) {
        if (!drawing.ownerEquals(member))
            throw new DrawingOwnerException();
    }
}
