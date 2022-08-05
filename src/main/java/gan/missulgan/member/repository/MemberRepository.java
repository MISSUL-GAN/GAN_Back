package gan.missulgan.member.repository;

import java.util.Optional;

import gan.missulgan.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByAccountEmail(String email);

}
