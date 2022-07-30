package gan.missulgan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
public class Nft extends DateTimeEntity {

    @Id @GeneratedValue
    @Column(name = "nft_id")
    private Long id;

    private String asset_contract_address;
    private String token_id;

    @OneToOne(mappedBy = "nft", fetch = LAZY)
    private Drawing drawing;

    //==Setter 대체==//
    public void changeDrawing(Drawing drawing) {
        this.drawing = drawing;
    }
}
