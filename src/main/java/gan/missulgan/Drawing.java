package gan.missulgan;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
public class Drawing {

    @Id @GeneratedValue
    @Column(name = "drawing_id")
    private Long id;

    private String title;
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "nft_id")
    private Nft nft;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;


    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getDrawings().add(this);
    }
    public void setNft(Nft nft) {
        this.nft = nft;
        nft.changeDrawing(this);
    }

}
