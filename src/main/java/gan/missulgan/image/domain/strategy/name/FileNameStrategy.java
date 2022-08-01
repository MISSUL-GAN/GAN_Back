package gan.missulgan.image.domain.strategy.name;

import java.io.InputStream;

public interface FileNameStrategy {

	String encodeName(InputStream inputStream);
}
