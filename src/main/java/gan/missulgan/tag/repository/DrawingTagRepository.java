package gan.missulgan.tag.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.tag.domain.DrawingTag;
import gan.missulgan.tag.domain.Tag;

@Repository
public interface DrawingTagRepository extends JpaRepository<DrawingTag, Long> {

	@Query("select distinct d.drawing "
		+ "from DrawingTag d "
		+ "where not exists "
		+ "(select dt.drawing "
		+ "from DrawingTag dt "
		+ "where d.drawing = dt.drawing and dt.tag not in :tags)")
	List<Drawing> findAllByAndTags(@Param("tags") Set<Tag> tags, Pageable pageable);

	@Query("select distinct d.drawing from DrawingTag d where d.tag in :tags")
	List<Drawing> findAllByOrTags(@Param("tags") Set<Tag> tags, Pageable pageable);

	@Query(value = "select distinct d.drawing from DrawingTag d where d.tag in :tags order by rand()", nativeQuery = true)
	List<Drawing> findAllByOrTagsRandom(@Param("tags") Set<Tag> tags, Pageable pageable);
}
