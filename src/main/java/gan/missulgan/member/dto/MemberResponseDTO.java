package gan.missulgan.member.dto;

import gan.missulgan.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
public class MemberResponseDTO {

    private final Long id;
    private final String name;
    private final String profileImage;
    private final String accountEmail;

    public static MemberResponseDTO from(Member member) {
        return MemberResponseDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .accountEmail(member.getAccountEmail())
                .profileImage(member.getProfileImage())
                .build();
    }
}
