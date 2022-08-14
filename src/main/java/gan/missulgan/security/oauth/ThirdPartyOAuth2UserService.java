package gan.missulgan.security.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gan.missulgan.member.domain.Member;
import gan.missulgan.member.repository.MemberRepository;
import gan.missulgan.security.auth.dto.OAuthUserImpl;
import gan.missulgan.security.oauth.dto.OAuth2UserAttribute;
import gan.missulgan.security.oauth.dto.OAuth2UserAttributeFactory;
import gan.missulgan.security.oauth.dto.SavedMemberDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThirdPartyOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2UserAttribute attributes = OAuth2UserAttributeFactory.of(registrationId, oAuth2User.getAttributes());
		SavedMemberDTO savedMemberDTO = save(attributes);
		return new OAuthUserImpl(savedMemberDTO, attributes.getAttributes());
	}

	private SavedMemberDTO save(OAuth2UserAttribute userAttribute) {
		String email = userAttribute.getEmail();
		String profileImage = userAttribute.getProfileImage();
		return memberRepository.findByAccountEmail(email)
			.map(entity -> {
				Member updatedMember = entity.updateProfileImage(profileImage);
				return SavedMemberDTO.from(updatedMember);
			})
			.orElseGet(() -> {
				Member savedMember = memberRepository.save(userAttribute.toEntity());
				return SavedMemberDTO.from(savedMember, true);
			});
	}
}