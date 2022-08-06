package gan.missulgan.member.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import gan.missulgan.DateTimeEntity;
import gan.missulgan.drawing.domain.Drawing;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Member extends DateTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;

	@NotNull
	private String provider;

	@NotNull
	private String profileNickname;

	@NotNull
	private String profileImage;

	@NotNull
	@Column(unique = true)
	private String accountEmail;

	@NotNull
	@Length(max = 24)
	private String userNickname;

	@OneToMany(mappedBy = "member")
	private List<Drawing> drawings = new ArrayList();

	@Builder
	public Member(Role role, String provider, String profileNickname, String profileImage, String accountEmail,
				  String userNickname) {
		this.role = role;
		this.provider = provider;
		this.profileNickname = profileNickname;
		this.profileImage = profileImage;
		this.accountEmail = accountEmail;
		this.userNickname = userNickname;
	}

	public Member updateProfileImage(String profileImage) {
		this.profileImage = profileImage;
		return this;
	}

	public Member setUserNickname(String nickname) {
		this.userNickname = nickname;
		return this;
	}
}
