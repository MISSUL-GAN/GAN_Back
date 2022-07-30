package gan.missulgan.member.domain;

import gan.missulgan.DateTimeEntity;
import gan.missulgan.Drawing;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Member extends DateTimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    private String profile_nickname;

    @NotNull
    private String profile_image;

    @NotNull
    private String account_email;

    @NotNull @Length(min = 4, max = 24)
    private String user_nickname;

    @OneToMany(mappedBy = "member")
    private List<Drawing> drawings = new ArrayList();

}
