package gan.missulgan.heart.domain;

import gan.missulgan.common.DateTimeEntity;
import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class Heart extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "drawing_id")
    private Drawing drawing;

    @Builder
    public Heart(Member member, Drawing drawing) {
        this.member = member;
        this.drawing = drawing;
    }

    public boolean didHeart(Long memberId) {
        return member.getId()
            .equals(memberId);
    }
}
