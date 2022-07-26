package gan.missulgan.drawing.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.member.dto.MemberResponseDTO;
import gan.missulgan.tag.dto.TagResponseDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DrawingResponseDTO {

	private final Long id;
	private final String title;
	private final String description;
	private final String fileName;
	@JsonFormat(
		shape = JsonFormat.Shape.STRING,
		pattern = "yyyy-MM-dd HH:mm:ss",
		locale = "Asia/Seoul"
	)
	private final LocalDateTime createdAt;
	private final MemberResponseDTO member;
	private final Set<TagResponseDTO> tags;
	private final Integer heartCount;
	private final Integer scrapCount;
	private String nftTransactionHash;
	private Boolean didHeart;
	private Boolean didScrap;

	public static DrawingResponseDTO from(Drawing drawing) {
		Set<TagResponseDTO> tags = drawing.getTags()
			.stream()
			.map(TagResponseDTO::from)
			.collect(Collectors.toSet());
		MemberResponseDTO member = MemberResponseDTO.from(drawing.getMember());

		return DrawingResponseDTO.builder()
			.id(drawing.getId())
			.title(drawing.getTitle())
			.description(drawing.getDescription())
			.fileName(drawing.getImage().getFileName())
			.createdAt(drawing.getCreatedAt())
			.member(member)
			.tags(tags)
			.nftTransactionHash(drawing.getNFTTransactionalHash())
			.heartCount(drawing.getHeartCount())
			.scrapCount(drawing.getScrapCount())
			.build();
	}

	public void putDidHeart(Boolean didHeart) {
		this.didHeart = didHeart;
	}

	public void putDidScrap(Boolean didScrap) {
		this.didScrap = didScrap;
	}
}
