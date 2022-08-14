package gan.missulgan.image.domain.strategy.store;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import gan.missulgan.image.domain.ImageType;
import lombok.RequiredArgsConstructor;

@Component
@Primary
@RequiredArgsConstructor
public class IPFSStoreStrategy implements FileStoreStrategy {

	@Value("${app.nftStorageToken}")
	private String NFT_STORAGE_ACCESS_TOKEN;
	private static final String UPLOAD_URI = "https://api.nft.storage/upload";
	private static final String IPFS_PREFIX = "https://ipfs.io/ipfs/";

	private final RestTemplate restTemplate;

	@Override
	public String store(byte[] bytes, ImageType imageType) throws IOException {
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = MediaType.valueOf(imageType.getContentType());
		headers.setContentType(mediaType);
		headers.setBearerAuth(NFT_STORAGE_ACCESS_TOKEN);
		HttpEntity<byte[]> request = new HttpEntity<>(bytes, headers);

		ResponseEntity<String> response = restTemplate.postForEntity(UPLOAD_URI, request, String.class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			String body = response.getBody();
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = (JsonObject)jsonParser.parse(body);
			jsonObject = jsonObject.getAsJsonObject("value");
			JsonPrimitive cid = jsonObject.getAsJsonPrimitive("cid");
			return cid.getAsString();
		}
		return null;
	}

	@Override
	public Resource load(String fileName) throws IOException {
		return new UrlResource(IPFS_PREFIX + fileName);
	}
}
