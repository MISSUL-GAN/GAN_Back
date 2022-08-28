package gan.missulgan.drawing.dto;

import gan.missulgan.nft.domain.NFT;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class NFTAddRequestDTO {

    @NotBlank
    private String assetContractAddress;
    @NotBlank
    private String tokenId;

    public NFT toEntity() {
        return new NFT(assetContractAddress, tokenId);
    }
}
