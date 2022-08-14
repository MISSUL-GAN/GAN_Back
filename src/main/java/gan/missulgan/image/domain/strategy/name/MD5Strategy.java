package gan.missulgan.image.domain.strategy.name;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
@Primary
public class MD5Strategy implements FileNameStrategy {

	@Override
	public String encodeName(byte[] bytes) {
		return DigestUtils.md5DigestAsHex(bytes);
	}
}
