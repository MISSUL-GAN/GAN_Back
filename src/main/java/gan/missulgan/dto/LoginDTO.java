package gan.missulgan.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginDTO {

    private Long id;

    private String profile_nickname;
    private String profile_image;
    private String account_email;

}
