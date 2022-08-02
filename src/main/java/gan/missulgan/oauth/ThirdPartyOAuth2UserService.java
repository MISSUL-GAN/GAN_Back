package gan.missulgan.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gan.missulgan.auth.dto.JwtUserDetails;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.domain.MemberRepository;
import gan.missulgan.oauth.dto.MemberInfoDTO;
import gan.missulgan.oauth.dto.OAuth2UserAttribute;
import gan.missulgan.oauth.dto.OAuth2UserAttributeFactory;
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
		return handleRequest(oAuth2User, userRequest);
	}

	private OAuth2User handleRequest(final OAuth2User oAuth2User, final OAuth2UserRequest userRequest) {
		final String registrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2UserAttribute attributes = OAuth2UserAttributeFactory.of(registrationId, oAuth2User.getAttributes());
		MemberInfoDTO memberInfoDTO = save(attributes);
		return new JwtUserDetails(memberInfoDTO, attributes.getAttributes());
	}

	private MemberInfoDTO save(OAuth2UserAttribute userAttribute) {
		String email = userAttribute.getEmail();
		String profileImage = userAttribute.getProfileImage();
		Member member = memberRepository.findByAccountEmail(email)
			.map(entity -> entity.updateProfileImage(profileImage))
			.orElseGet(() -> memberRepository.save(userAttribute.toEntity()));
		return MemberInfoDTO.from(member);
	}
}