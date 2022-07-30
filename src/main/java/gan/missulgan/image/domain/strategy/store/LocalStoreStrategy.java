package gan.missulgan.image.domain.strategy.store;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Primary
public class LocalStoreStrategy implements FileStoreStrategy {

	@Value("${app.localStorePath}")
	private String storePath;

	@Override
	public void store(MultipartFile file, String fileName) {
		File newFile = new File(getPathName(fileName));
		if (newFile.exists())
			return;
		try {
			file.transferTo(newFile);
		} catch (IOException e) {
			throw new RuntimeException(e); // TODO: replace with pre-defined exception
		}
	}

	@Override
	public Resource load(String fileName) {
		FileSystemResource resource = new FileSystemResource(getPathName(fileName));
		if (!resource.exists())
			throw new RuntimeException(); // TODO: replace with pre-defined exception
		return resource;
	}

	private String getPathName(String fileName) {
		return storePath + "/" + fileName;
	}
}
