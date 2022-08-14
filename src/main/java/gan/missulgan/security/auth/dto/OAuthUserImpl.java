package gan.missulgan.security.auth.dto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import gan.missulgan.security.oauth.dto.SavedMemberDTO;

public class OAuthUserImpl implements OAuth2User {

	private final SavedMemberDTO savedMemberDTO;
	private final Map<String, Object> attributes;

	public OAuthUserImpl(SavedMemberDTO savedMemberDTO, Map<String, Object> attributes) {
		this.savedMemberDTO = savedMemberDTO;
		this.attributes = attributes;
	}

	public SavedMemberDTO getSavedMemberDTO() {
		return savedMemberDTO;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of(new SimpleGrantedAuthority(savedMemberDTO.getRole()));
	}

	@Override
	public String getName() {
		return savedMemberDTO.getEmail();
	}
}