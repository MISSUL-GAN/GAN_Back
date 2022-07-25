package gan.missulgan.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String profile_nickname;
    private String profile_image;
    private String account_email;

}
