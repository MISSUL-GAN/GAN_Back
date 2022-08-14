package gan.missulgan.tag.domain;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "tag", fetch = LAZY)
    private Set<DrawingTag> drawingTags = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }
}
