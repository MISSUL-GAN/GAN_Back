package gan.missulgan.nft.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import gan.missulgan.common.DateTimeEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class NFT extends DateTimeEntity {

	@Id
	@GeneratedValue
	@Column(name = "nft_id")
	private Long id;

	private String transactionHash;

	public NFT(String transactionHash) {
		this.transactionHash = transactionHash;
	}
}
