package gan.missulgan.nft.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import gan.missulgan.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Nft extends DateTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "nft_id")
	private Long id;

	private String assetContractAddress;
	private String tokenId;

    public Nft(String assetContractAddress, String tokenId) {
        this.assetContractAddress = assetContractAddress;
        this.tokenId = tokenId;
    }
}
