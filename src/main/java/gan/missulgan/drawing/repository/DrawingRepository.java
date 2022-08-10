package gan.missulgan.drawing.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.member.domain.Member;
import gan.missulgan.tag.domain.DrawingTag;
import gan.missulgan.tag.domain.Tag;

@Repository
public interface DrawingRepository extends JpaRepository<Drawing, Long> {

	List<Drawing> findAllByMember(Member member, Pageable pageable);

	@Query(value = "select * from Drawing d order by rand() limit :size", nativeQuery = true)
	List<Drawing> findAllByRandom(Integer size);
}
