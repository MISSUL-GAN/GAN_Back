package gan.missulgan.image.domain.strategy.store;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;

public interface FileStoreStrategy {

	void store(InputStream inputStream, String fileName) throws IOException;

	Resource load(String fileName) throws IOException;
}
