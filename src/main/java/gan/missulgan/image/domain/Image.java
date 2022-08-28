package gan.missulgan.image.domain;

import static javax.persistence.GenerationType.*;

import gan.missulgan.common.DateTimeEntity;
import gan.missulgan.image.domain.strategy.store.FileStoreStrategy;
import gan.missulgan.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "image_id", nullable = false)
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
    public Image(Member member, ImageType imageType, byte[] bytes, String fileName) {
        this.member = member;
        this.imageType = imageType;
        this.bytes = bytes;
        this.fileName = fileName;
    }

    public void store(final FileStoreStrategy fileStoreStrategy) throws IOException {
        this.fileName = fileStoreStrategy.store(bytes, imageType);
    }

    public Resource load(final FileStoreStrategy fileStoreStrategy) throws IOException {
        return fileStoreStrategy.load(fileName);
    }
}
