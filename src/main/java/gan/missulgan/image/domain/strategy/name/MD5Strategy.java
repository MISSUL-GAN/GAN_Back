package gan.missulgan.image.domain.strategy.name;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
@Primary
public class MD5Strategy implements FileNameStrategy {

	@Override
	public String encodeName(InputStream inputStream) {
		try {
			return DigestUtils.md5DigestAsHex(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("MD5_FAIL");
		}
	}
}
