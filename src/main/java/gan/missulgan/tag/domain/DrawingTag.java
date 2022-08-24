package gan.missulgan.tag.domain;

import gan.missulgan.drawing.domain.Drawing;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Table(name = "drawing_tag")
@NoArgsConstructor
public class DrawingTag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "drawing_tag_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "drawing_id")
    private Drawing drawing;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public DrawingTag(Drawing drawing, Tag tag) {
        this.drawing = drawing;
        this.tag = tag;
    }
}
