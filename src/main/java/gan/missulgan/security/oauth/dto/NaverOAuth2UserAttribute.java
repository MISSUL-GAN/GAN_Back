package gan.missulgan.security.oauth.dto;

import gan.missulgan.member.domain.Member;
import gan.missulgan.member.domain.Role;

import java.util.Map;

public class NaverOAuth2UserAttribute extends OAuth2UserAttribute {

    private static final String NAVER_PROVIDER_ID = "naver";
    private final Map<String, Object> profile;


    public NaverOAuth2UserAttribute(Map<String, Object> attributes) {
        super(attributes);
        this.profile = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public Member toEntity() {
        return Member.builder()
                .role(Role.USER)
                .provider(NAVER_PROVIDER_ID)
                .profileImage(getProfileImage())
                .name(getNickname())
                .accountEmail(getEmail())
                .build();
    }

    @Override
    public String getEmail() {
        return profile.get("email").toString();
    }

    @Override
    public String getNickname() {
        return profile.get("name").toString();
    }

    @Override
    public String getProfileImage() {
        return profile.get("profile_image").toString();
    }
}
