package gan.missulgan.drawing.dto;

import gan.missulgan.nft.domain.Nft;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class NftAddRequestDTO {

    @NotBlank
    private String assetContractAddress;
    @NotBlank
    private String tokenId;

    public Nft toEntity() {
        return new Nft(assetContractAddress, tokenId);
    }
}
