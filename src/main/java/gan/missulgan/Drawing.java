package gan.missulgan;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
public class Drawing extends DateTimeEntity {

    @Id @GeneratedValue
    @Column(name = "drawing_id")
    private Long id;

    @NotNull @Length(max = 16)
    private String title;

    @NotNull @Length(max = 100)
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY) // cascade = ALL, orphanRemoval = true
    @JoinColumn(name = "nft_id")
    private Nft nft;

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
