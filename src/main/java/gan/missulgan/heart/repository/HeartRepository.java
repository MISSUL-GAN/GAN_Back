package gan.missulgan.heart.repository;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.heart.domain.Heart;
import gan.missulgan.member.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByMemberAndDrawing(Member member, Drawing drawing);

    Long countByDrawing(Drawing drawing);

    @Query(value = "select h.member from Heart h where h.drawing = :drawing")
    List<Member> findHeartedMembers(@Param("drawing") Drawing drawing, Pageable pageable);

}
