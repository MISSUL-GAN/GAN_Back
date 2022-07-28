package gan.missulgan;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@ToString
public class Heart {

    @Id @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Long member_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "drawing_id")
    private Long drawing_id;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

}
