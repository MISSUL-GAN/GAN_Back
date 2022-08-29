package gan.missulgan.nft.domain;

import static javax.persistence.GenerationType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import gan.missulgan.common.DateTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class NFT extends DateTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "nft_id")
	private Long id;

	private String transactionHash;

	public NFT(String transactionHash) {
		this.transactionHash = transactionHash;
	}
}
