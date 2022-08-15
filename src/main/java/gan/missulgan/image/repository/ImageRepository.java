package gan.missulgan.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import gan.missulgan.image.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findImageByFileName(String fileName);
}
