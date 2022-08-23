package gan.missulgan.tag.repository;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.tag.domain.DrawingTag;
import gan.missulgan.tag.domain.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DrawingTagRepository extends JpaRepository<DrawingTag, Long> {

    @Query("select distinct d.drawing "
            + "from DrawingTag d "
            + "where not exists "
            + "(select dt.drawing "
            + "from DrawingTag dt "
            + "where d.drawing = dt.drawing and dt.tag not in :tags)")
    List<Drawing> findAllByAndTags(@Param("tags") Set<Tag> tags, Pageable pageable);

    @Query("select distinct d.drawing from DrawingTag d where d.tag in :tags order by d.drawing.hearts.size")
    List<Drawing> findAllByOrTagsOrderByHeartCount(@Param("tags") Set<Tag> tags, Pageable pageable);

    @Query(value = "select distinct drawing_id from drawing_tag where tag_id in :tags order by rand()", nativeQuery = true)
    List<Long> findAllByOrTagsRandom(@Param("tags") Set<Tag> tags, Pageable pageable);

    @Query("select distinct d.drawing from DrawingTag d where d.tag in :tags order by d.drawing.createdAt DESC")
    List<Drawing> findAllByOrTagsOrderByIdDesc(@Param("tags") Set<Tag> tags, Pageable pageable);
}
