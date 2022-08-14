package gan.missulgan.nft.repository;

import gan.missulgan.nft.domain.Nft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {
}
