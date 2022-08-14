package gan.missulgan.heart.domain;

import gan.missulgan.DateTimeEntity;
import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Heart extends DateTimeEntity {

    @Id @GeneratedValue
    @Column(name = "like_id")
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
}
