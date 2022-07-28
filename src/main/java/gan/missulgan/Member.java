package gan.missulgan;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String profile_nickname;
    private String profile_image;
    private String account_email;
    private String user_nickname;

    @OneToMany(mappedBy = "member")
    private List<Drawing> drawings = new ArrayList();

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

}
