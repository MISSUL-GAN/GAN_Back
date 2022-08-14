package gan.missulgan.scrap.service;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.member.domain.Member;
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

    @Transactional
    public List<DrawingResponseDTO> findScraps(Member member, Pageable pageable) {
        return scrapRepository.findScrapDrawing(member, pageable).stream()
                .map(DrawingResponseDTO::from)
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
