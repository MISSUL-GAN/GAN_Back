package gan.missulgan.security.oauth.dto;

import gan.missulgan.member.domain.Member;

import java.util.HashMap;
import java.util.Map;

public abstract class OAuth2UserAttribute {

    private final Map<String, Object> attributes;

    public OAuth2UserAttribute(Map<String, Object> attributes) {
        this.attributes = new HashMap<>(attributes);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getEmail();

    public abstract String getNickname();

    public abstract String getProfileImage();

    public abstract Member toEntity();
}
