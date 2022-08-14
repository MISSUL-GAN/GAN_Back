package gan.missulgan.drawing.dto;

import gan.missulgan.nft.domain.Nft;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NftResponseDTO {

    private final String assetContractAddress;
    private final String tokenId;

    public static NftResponseDTO from(Nft nft) {
        if (nft != null)
            return new NftResponseDTO(nft.getAssetContractAddress(), nft.getTokenId());
        return null;
    }
}
