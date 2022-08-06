package gan.missulgan.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gan.missulgan.tag.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
