package gan.missulgan.image.domain;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.core.io.Resource;

import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;
import gan.missulgan.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ImageType imageType;

	@Column(nullable = false)
	private String fileName;

	@Transient
	private byte[] bytes;

	@Builder
	public Image(Member member, ImageType imageType, byte[] bytes) {
		this.member = member;
		this.imageType = imageType;
		this.bytes = bytes;
	}

	public void store(final FileStoreStrategy fileStoreStrategy) throws IOException {
		this.fileName = fileStoreStrategy.store(bytes, imageType);
	}

	public Resource load(final FileStoreStrategy fileStoreStrategy) throws IOException {
		return fileStoreStrategy.load(fileName);
	}
}
