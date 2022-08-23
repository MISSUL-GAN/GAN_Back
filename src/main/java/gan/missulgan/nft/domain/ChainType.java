package gan.missulgan.nft.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ChainType {
    POLYGON("polygon"), RINKEBY("rinkeby");

    private final String value;

}
