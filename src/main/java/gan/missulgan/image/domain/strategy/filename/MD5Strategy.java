package gan.missulgan.image.domain.strategy.filename;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
@Primary
public class MD5Strategy implements FileNameStrategy {

	@Override
	public String encodeFileName(String fileName) {
		return DigestUtils.md5DigestAsHex(fileName.getBytes());
	}
}
