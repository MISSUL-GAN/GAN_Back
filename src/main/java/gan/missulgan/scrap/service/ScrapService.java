package gan.missulgan.scrap.service;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.dto.MemberResponseDTO;
import gan.missulgan.scrap.domain.Scrap;
import gan.missulgan.scrap.exception.BadScrapException;
import gan.missulgan.scrap.exception.ScrapDuplicateException;
import gan.missulgan.scrap.exception.ScrapOwnerException;
import gan.missulgan.scrap.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;

    @Transactional
    public void scrap(Member member, Drawing drawing) {
        checkIsOwner(member, drawing);
        checkDuplicate(member, drawing);

        Scrap scrap = new Scrap(member, drawing);
        scrapRepository.save(scrap);
    }

    @Transactional
    public void unScrap(Member member, Drawing drawing) {
        Scrap scrap = getScrap(member, drawing);
        scrapRepository.delete(scrap);
    }

    private DrawingResponseDTO buildDrawingResponseDTO(Drawing drawing, Member member) { // TODO: 제거하고 별도의 endpoint로..!
        Long memberId = member.getId();
        DrawingResponseDTO drawingResponseDTO = DrawingResponseDTO.from(drawing);
        drawingResponseDTO.putDidHeart(drawing.didHeart(memberId));
        drawingResponseDTO.putDidScrap(drawing.didScrap(memberId));
        return drawingResponseDTO;
    }

    @Transactional(readOnly = true)
    public List<DrawingResponseDTO> findScrappedDrawings(Member member, Pageable pageable) {
        return scrapRepository.findScrappedDrawings(member, pageable).stream()
                .map(drawing -> buildDrawingResponseDTO(drawing, member))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> findScrappedMembers(Drawing drawing, Pageable pageable) {
        return scrapRepository.findScrappedMembers(drawing, pageable).stream()
                .map(MemberResponseDTO::from)
                .collect(Collectors.toList());
    }

    private void checkIsOwner(Member member, Drawing drawing) {
        if (drawing.ownerEquals(member)) {
            throw new ScrapOwnerException();
        }
    }

    private void checkDuplicate(Member member, Drawing drawing) {
        if (scrapRepository.findByMemberAndDrawing(member, drawing).isPresent()) {
            throw new ScrapDuplicateException();
        }
    }

    private Scrap getScrap(Member member, Drawing drawing) {
        return scrapRepository.findByMemberAndDrawing(member, drawing)
                .orElseThrow(BadScrapException::new);
    }
}
