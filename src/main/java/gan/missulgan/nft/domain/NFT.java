package gan.missulgan.nft.domain;

import gan.missulgan.common.DateTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class NFT extends DateTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "nft_id")
    private Long id;

    private String assetContractAddress;
    private String tokenId;

    public NFT(String assetContractAddress, String tokenId) {
        this.assetContractAddress = assetContractAddress;
        this.tokenId = tokenId;
    }
}
