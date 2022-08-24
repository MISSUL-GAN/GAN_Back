package gan.missulgan.scrap.domain;

import gan.missulgan.common.DateTimeEntity;
import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Scrap extends DateTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "scrap_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "drawing_id")
    private Drawing drawing;

    @Builder
    public Scrap(Member member, Drawing drawing) {
        this.member = member;
        this.drawing = drawing;
    }
}
