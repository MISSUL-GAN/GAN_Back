package gan.missulgan.scrap.repository;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.member.domain.Member;
import gan.missulgan.scrap.domain.Scrap;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    Optional<Scrap> findByMemberAndDrawing(Member member, Drawing drawing);

    Long countByMember(Member member);

    @Query(value = "select s.drawing from Scrap s where s.member = :member")
    List<Drawing> findScrapedDrawing(@Param("member") Member member, Pageable pageable);

}
