package gan.missulgan.image.domain.strategy.store;

import gan.missulgan.image.domain.ImageType;
import gan.missulgan.image.domain.strategy.name.FileNameStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
public class LocalStoreStrategy implements FileStoreStrategy {

    @Value("${app.localStorePath}")
    private String storePath;

    private final FileNameStrategy fileNameStrategy;

    @Override
    public String store(byte[] bytes, ImageType imageType) throws IOException {
        String fileName = fileNameStrategy.encodeName(bytes);
        File newFile = new File(getPathName(fileName));
        if (newFile.exists())
            return fileName;
        writeToFile(bytes, newFile);
        return fileName;
    }

    @Override
    public Resource load(String fileName) throws IOException {
        FileSystemResource resource = new FileSystemResource(getPathName(fileName));
        if (!resource.exists())
            throw new IOException("FILE_LOAD_FAIL");
        return resource;
    }

    private void writeToFile(byte[] bytes, File file) throws IOException {
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
