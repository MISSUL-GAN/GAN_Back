package gan.missulgan.image.domain.strategy.store;

import gan.missulgan.image.domain.ImageType;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface FileStoreStrategy {

    String store(byte[] bytes, ImageType imageType) throws IOException;

    Resource load(String fileName) throws IOException;
}
