package gan.missulgan.image.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import gan.missulgan.image.dto.SavedImageDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false)
	private Long memberId;

	@Column(nullable = false)
	private String storedFileName;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ImageType imageType;

	@Column(nullable = false)
	private String fileName;

	@Builder
	public Image(Long memberId, String storedFileName, ImageType imageType, String fileName) {
		this.memberId = memberId;
		this.storedFileName = storedFileName;
		this.imageType = imageType;
		this.fileName = fileName;
	}

	public SavedImageDTO toDTO() {
		return SavedImageDTO.builder()
			.fileName(storedFileName)
			.type(imageType)
			.build();
	}
}
