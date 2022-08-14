package gan.missulgan.drawing.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.member.domain.Member;

@Repository
public interface DrawingRepository extends JpaRepository<Drawing, Long> {

	List<Drawing> findAllByMember(Member member, Pageable pageable);

	@Query(value = "select * from Drawing d order by rand()", nativeQuery = true)
	List<Drawing> findAllByRandom(Pageable pageable);

	@Query(value = "select d from Drawing d order by d.hearts.size")
	List<Drawing> findAllOrderByHeartCount(Pageable pageable);
}
