package gan.missulgan.heart.service;
import gan.missulgan.heart.domain.Heart;
import gan.missulgan.common.ExceptionEnum;
import gan.missulgan.common.exception.ForbiddenException;
import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.heart.exception.HeartNotFoundException;
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
    public List<MemberDTO> getHearts(Drawing drawing, Pageable pageable) {
        return  heartRepository.findHeartMembers(drawing, pageable).stream()
                .map(MemberDTO::from)
                .collect(Collectors.toList());
    }

    private static void checkIsOwner(Member member, Drawing drawing) {
        if (member.equals(drawing.getMember())) {
            throw new ForbiddenException(ExceptionEnum.HEART_OWNER);
        }
    }

    private void checkDuplicate(Member member, Drawing drawing) {
        if (heartRepository.findByMemberAndDrawing(member, drawing).isPresent()) {
            throw new ForbiddenException(ExceptionEnum.HEART_DONE);
        }
    }

    private Heart getHeart(Member member, Drawing drawing) {
        return heartRepository.findByMemberAndDrawing(member, drawing)
                .orElseThrow(HeartNotFoundException::new);
    }
}
