package gan.missulgan.image.domain.strategy.store;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStoreStrategy {

	void store(MultipartFile file, String fileName);

	Resource load(String fileName);
}
