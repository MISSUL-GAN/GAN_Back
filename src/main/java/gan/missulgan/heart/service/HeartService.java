package gan.missulgan.heart.service;
import gan.missulgan.heart.domain.Heart;
import gan.missulgan.common.exception.ForbiddenException;
import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.heart.exception.BadHeartException;
import gan.missulgan.heart.repository.HeartRepository;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;

    @Transactional(readOnly = true)
    public Long getHeartCounting(Drawing drawing) {
        return heartRepository.countByDrawing(drawing);
    }

    @Transactional
    public void heart(Member member, Drawing drawing) {
        checkIsOwner(member, drawing);
        checkDuplicate(member, drawing);

        Heart heart = new Heart(member, drawing);
        heartRepository.save(heart);
    }

    @Transactional
    public void unHeart(Member member, Drawing drawing) {
        Heart heart = getHeart(member, drawing);
        heartRepository.delete(heart);
    }

    @Transactional
    public List<MemberDTO> findHearts(Drawing drawing, Pageable pageable) {
        return  heartRepository.findHeartMembers(drawing, pageable).stream()
                .map(MemberDTO::from)
                .collect(Collectors.toList());
    }

    private static void checkIsOwner(Member member, Drawing drawing) {
        if (member.equals(drawing.getMember())) {
            throw new ForbiddenException("본인 작품에는 좋아요를 누를 수 없습니다.");
        }
    }

    private void checkDuplicate(Member member, Drawing drawing) {
        if (heartRepository.findByMemberAndDrawing(member, drawing).isPresent()) {
            throw new ForbiddenException("좋아요는 한 번만 누를 수 있습니다.");
        }
    }

    private Heart getHeart(Member member, Drawing drawing) {
        return heartRepository.findByMemberAndDrawing(member, drawing)
                .orElseThrow(BadHeartException::new);
    }
}
