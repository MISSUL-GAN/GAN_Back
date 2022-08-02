package gan.missulgan.image.domain.strategy.store;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
@Primary
public class LocalStoreStrategy implements FileStoreStrategy {

	@Value("${app.localStorePath}")
	private String storePath;

	@Override
	public void store(byte [] bytes, String fileName) throws IOException {
		File newFile = new File(getPathName(fileName));
		if (newFile.exists())
			return;
		writeToFile(bytes, newFile);
	}

	@Override
	public Resource load(String fileName) throws IOException {
		FileSystemResource resource = new FileSystemResource(getPathName(fileName));
		if (!resource.exists())
			throw new IOException("FILE_LOAD_FAIL");
		return resource;
	}

	private void writeToFile(byte [] bytes, File file) throws IOException {
		try {
			FileCopyUtils.copy(bytes, file);
		} catch (IOException e) {
			throw new IOException("FILE_WRITE_FAIL");
		}
	}

	private String getPathName(String fileName) {
		return storePath + File.separator + fileName;
	}
}
