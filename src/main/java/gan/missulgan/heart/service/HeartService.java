package gan.missulgan.heart.service;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.heart.domain.Heart;
import gan.missulgan.heart.exception.BadHeartException;
import gan.missulgan.heart.exception.HeartDuplicateException;
import gan.missulgan.heart.exception.HeartOwnerException;
import gan.missulgan.heart.repository.HeartRepository;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.dto.MemberResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;

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

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> findHeartedMembers(Drawing drawing, Pageable pageable) {
        return heartRepository.findHeartedMembers(drawing, pageable).stream()
                .map(MemberResponseDTO::from)
                .collect(Collectors.toList());
    }

    private void checkIsOwner(Member member, Drawing drawing) {
        if (drawing.ownerEquals(member)) {
            throw new HeartOwnerException();
        }
    }

    private void checkDuplicate(Member member, Drawing drawing) {
        if (heartRepository.findByMemberAndDrawing(member, drawing).isPresent()) {
            throw new HeartDuplicateException();
        }
    }

    private Heart getHeart(Member member, Drawing drawing) {
        return heartRepository.findByMemberAndDrawing(member, drawing)
                .orElseThrow(BadHeartException::new);
    }
}
