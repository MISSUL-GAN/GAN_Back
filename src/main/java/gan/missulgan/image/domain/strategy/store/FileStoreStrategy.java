package gan.missulgan.image.domain.strategy.store;

import java.io.IOException;

import org.springframework.core.io.Resource;

public interface FileStoreStrategy {

	void store(byte[] bytes, String fileName) throws IOException;

	Resource load(String fileName) throws IOException;
}
