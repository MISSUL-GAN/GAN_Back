package gan.missulgan.auth.dto;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import gan.missulgan.oauth.dto.MemberInfoDTO;

public class JwtUserDetails implements UserDetails, OAuth2User {

	private final String email;
	private final String role;
	private final Map<String, Object> attributes;

	public JwtUserDetails(MemberInfoDTO memberInfoDTO, Map<String, Object> attributes) {
		this.email = memberInfoDTO.getEmail();
		this.role = memberInfoDTO.getRole();
		this.attributes = attributes;
	}

	public String getRole() {
		return role;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getName() {
		return getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}