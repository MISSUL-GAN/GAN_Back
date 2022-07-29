package gan.missulgan.image.domain.strategy.store;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoreStrategy {

	void store(MultipartFile file, String fileName);
}
