package gan.missulgan.scrap.service;

import gan.missulgan.common.exception.ForbiddenException;
import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.member.domain.Member;
import gan.missulgan.scrap.domain.Scrap;
import gan.missulgan.scrap.exception.BadScrapException;
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

    @Transactional(readOnly = true)
    public Long getScrapCounting(Member member) {
        return scrapRepository.countByMember(member);
    }

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

    private static void checkIsOwner(Member member, Drawing drawing) {
        if (member.equals(drawing.getMember())) {
            throw new ForbiddenException("본인 작품에는 스크랩을 누를 수 없습니다.");
        }
    }

    private void checkDuplicate(Member member, Drawing drawing) {
        if (scrapRepository.findByMemberAndDrawing(member, drawing).isPresent()) {
            throw new ForbiddenException("스크랩은 한 번만 누를 수 있습니다.");
        }
    }

    private Scrap getScrap(Member member, Drawing drawing) {
        return scrapRepository.findByMemberAndDrawing(member, drawing)
                .orElseThrow(BadScrapException::new);
    }
}
