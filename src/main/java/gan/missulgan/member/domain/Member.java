package gan.missulgan.member.domain;

import gan.missulgan.common.DateTimeEntity;
import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.scrap.domain.Scrap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Member extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @NotNull
    private String provider;

    @NotNull
    private String profileImage;

    @NotNull
    @Column(unique = true)
    private String accountEmail;

    @NotNull
    @Length(max = 24)
    private String name;

    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private List<Drawing> drawings = new ArrayList();

    @OneToMany(mappedBy = "member", cascade = REMOVE, orphanRemoval = true)
    private List<Scrap> scraps = new ArrayList<>();

    @Builder
    public Member(Role role, String provider, String profileImage, String accountEmail,
                  String name) {
        this.role = role;
        this.provider = provider;
        this.profileImage = profileImage;
        this.accountEmail = accountEmail;
        this.name = name;
    }

    public Member updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public Member updateName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Member member = (Member) o;

        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
