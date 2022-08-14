package gan.missulgan.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class AuthMemberDTO {

    private final Long id;
    private final String email;
    private final String role;
}
