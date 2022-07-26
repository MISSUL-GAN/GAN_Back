package gan.missulgan.drawing.repository;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawingRepository extends JpaRepository<Drawing, Long> {

    List<Drawing> findAllByMember(Member member, Pageable pageable);

    @Query(value = "select * from drawing d order by rand() limit 20;", nativeQuery = true)
    List<Drawing> findAllByRandom();

    @Query(value = "select d from Drawing d order by d.hearts.size DESC")
    List<Drawing> findAllOrderByHeartCount(Pageable pageable);

    List<Drawing> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
