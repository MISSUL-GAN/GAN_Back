package gan.missulgan.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserNicknameDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserNicknameRequest {
        private String userNickname;
        private String accountEmail;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserNicknameResponse {
        private String userNickname;
    }

}
