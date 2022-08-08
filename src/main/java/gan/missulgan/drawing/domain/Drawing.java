package gan.missulgan.drawing.domain;

import static javax.persistence.FetchType.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import gan.missulgan.DateTimeEntity;
import gan.missulgan.Nft;
import gan.missulgan.member.domain.Member;
import gan.missulgan.tag.domain.DrawingTag;
import gan.missulgan.tag.domain.Tag;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
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
	private String fileName;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "drawing", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<DrawingTag> tags = new HashSet<>();

	@OneToOne(fetch = LAZY) // cascade = ALL, orphanRemoval = true
	@JoinColumn(name = "nft_id")
	private Nft nft;

	@Builder
	public Drawing(String title, String description, String fileName, Member member, Set<DrawingTag> tags) {
		this.title = title;
		this.description = description;
		this.fileName = fileName;
		this.member = member;
		this.tags = tags;
	}

	//==연관관계 메서드==//
	public void setMember(Member member) {
		this.member = member;
		member.getDrawings().add(this);
	}

	public void setNft(Nft nft) {
		this.nft = nft;
		nft.changeDrawing(this);
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
}
