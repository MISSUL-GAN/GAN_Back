package gan.missulgan.drawing.domain;

import static javax.persistence.FetchType.*;

import gan.missulgan.common.DateTimeEntity;
import gan.missulgan.heart.domain.Heart;
import gan.missulgan.image.domain.Image;
import gan.missulgan.member.domain.Member;
import gan.missulgan.nft.domain.Nft;
import gan.missulgan.scrap.domain.Scrap;
import gan.missulgan.tag.domain.DrawingTag;
import gan.missulgan.tag.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class Drawing extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drawing_id")
    private Long id;

    @NotNull
    @Length(max = 40)
    private String title;

    @NotNull
    @Length(max = 400)
    private String description;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @NotNull
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "drawing", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DrawingTag> tags = new HashSet<>();

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "drawing", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = LAZY)
    private List<Heart> hearts = new ArrayList<>();

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "drawing", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = LAZY)
    private List<Scrap> scraps = new ArrayList<>();

    @OneToOne(fetch = LAZY) // cascade = ALL, orphanRemoval = true
    @JoinColumn(name = "nft_id")
    private Nft nft;

    @Builder
    public Drawing(String title, String description, Image image, Member member, Set<DrawingTag> tags, Nft nft) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.member = member;
        this.tags = tags;
        this.nft = nft;
    }

    public void putNftInfo(Nft nft) {
        this.nft = nft;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags.stream()
                .map(tag -> new DrawingTag(this, tag))
                .collect(Collectors.toSet());
    }

    public Set<Tag> getTags() {
        return tags.stream()
                .map(DrawingTag::getTag)
                .collect(Collectors.toSet());
    }

    public int getHeartCount() {
        return hearts.size();
    }

    public int getScrapCount() {
        return scraps.size();
    }

    public boolean ownerEquals(Member member) {
        return this.member.equals(member);
    }
}
