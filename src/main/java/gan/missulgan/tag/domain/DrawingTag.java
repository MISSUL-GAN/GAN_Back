package gan.missulgan.tag.domain;

import gan.missulgan.drawing.domain.Drawing;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "drawing_tag")
@NoArgsConstructor
public class DrawingTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drawing_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drawing_id")
    private Drawing drawing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public DrawingTag(Drawing drawing, Tag tag) {
        this.drawing = drawing;
        this.tag = tag;
    }
}
