package gan.missulgan.tag.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.tag.domain.DrawingTag;

@Repository
public interface DrawingTagRepository extends JpaRepository<DrawingTag, Long> {

	@Query("select distinct d.drawing from DrawingTag d where d.tag.id in :ids" )
	Page<Drawing> findAllById(@Param("ids") Set<Long> ids, Pageable pageable);
}
