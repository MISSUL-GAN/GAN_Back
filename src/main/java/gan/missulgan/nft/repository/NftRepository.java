package gan.missulgan.nft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gan.missulgan.nft.domain.Nft;

@Repository
public interface NftRepository extends JpaRepository<Nft, Long> {
}
