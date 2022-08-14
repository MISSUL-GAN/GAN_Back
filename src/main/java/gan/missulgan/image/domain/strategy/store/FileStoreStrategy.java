package gan.missulgan.image.domain.strategy.store;

import java.io.IOException;

import org.springframework.core.io.Resource;

import gan.missulgan.image.domain.ImageType;

public interface FileStoreStrategy {

	String store(byte[] bytes, ImageType imageType) throws IOException;

	Resource load(String fileName) throws IOException;
}
