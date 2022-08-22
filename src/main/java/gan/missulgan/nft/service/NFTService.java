package gan.missulgan.nft.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.nimbusds.jose.shaded.json.JSONObject;
import gan.missulgan.image.domain.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class NFTService {

    @Value("${app.nftAuthorizationKey}")
    private static String NFT_AUTHORIZATION_KEY;
    private static final String NFT_PORT_URL = "https://api.nftport.xyz/v0/metadata";
    private static final String IPFS_PREFIX = "https://ipfs.io/ipfs/";
    private final RestTemplate restTemplate;

    public Resource imagefileToIPFS(byte[] bytes, ImageType imageType) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.valueOf(imageType.getContentType());
        headers.setContentType(mediaType);
        headers.set("Authorization", NFT_AUTHORIZATION_KEY);

        HttpEntity<byte[]> request = new HttpEntity<>(bytes, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(NFT_PORT_URL, request, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String body = response.getBody();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(body);
            JsonPrimitive ipfs_url = jsonObject.getAsJsonPrimitive("ipfs_url");
            return new UrlResource(IPFS_PREFIX + ipfs_url.getAsString());
        }
        return null;
    }

    // TODO : name/description(FROM Drawing), file_url(FROM Image)
    public String metadataToIPFS(String name, String description, String file_url) throws IOException {
        // Request Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", NFT_AUTHORIZATION_KEY);

        // Request Body
        JSONObject requestJson = new JSONObject();
        requestJson.put("name", name);
        requestJson.put("description", description);
        requestJson.put("file_url", file_url);

        // Request
        HttpEntity<JSONObject> request = new HttpEntity<>(requestJson, headers);

        // Response
        ResponseEntity<String> response =
                restTemplate.postForEntity(NFT_PORT_URL, request, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String body = response.getBody();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(body);
            JsonPrimitive metadata_uri = jsonObject.getAsJsonPrimitive("metadata_uri");
            return metadata_uri.getAsString();
        }
        return null;
    }
}
