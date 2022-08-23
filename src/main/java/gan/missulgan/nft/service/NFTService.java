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

    @Value("${app.nftStorageToken}")
    private String NFT_STORAGE_ACCESS_TOKEN;

    @Value("${app.nftAuthorizationKey}")
    private static String NFT_AUTHORIZATION_KEY;

    private static final String IMAGE_IPFS_URL = "https://api.nft.storage/upload";
    private static final String IPFS_PREFIX = "https://ipfs.io/ipfs/";
    private static final String METADATA_IPFS_URL = "https://api.nftport.xyz/v0/metadata";
    private static final String MINTING_URL = "https://api.nftport.xyz/v0/mints/customizable";

    private final RestTemplate restTemplate;

    public Resource imageToIPFS(byte[] bytes, ImageType imageType) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.valueOf(imageType.getContentType());
        headers.setContentType(mediaType);
        headers.setBearerAuth(NFT_STORAGE_ACCESS_TOKEN);
        HttpEntity<byte[]> request = new HttpEntity<>(bytes, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(IMAGE_IPFS_URL, request, String.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String body = response.getBody();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(body);
            jsonObject = jsonObject.getAsJsonObject("value");
            JsonPrimitive cid = jsonObject.getAsJsonPrimitive("cid");
            String ipfs = cid.getAsString();
            return new UrlResource(IPFS_PREFIX + ipfs);
        }
        return null;
    }

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
                restTemplate.postForEntity(METADATA_IPFS_URL, request, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String body = response.getBody();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(body);
            JsonPrimitive metadata_uri = jsonObject.getAsJsonPrimitive("metadata_uri");
            return metadata_uri.getAsString();
        }
        return null;
    }

    public HttpStatus minting(String contract_address, String metadata_uri, String mint_to_address) throws IOException {
        // Request Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", NFT_AUTHORIZATION_KEY);

        // Request Body
        JSONObject requestJson = new JSONObject();
        requestJson.put("chain", "polygon");
        requestJson.put("contract_address", contract_address);
        requestJson.put("metadata_uri", metadata_uri);
        requestJson.put("mint_to_address", mint_to_address);

        // Request
        HttpEntity<JSONObject> request = new HttpEntity<>(requestJson, headers);

        // Response
        ResponseEntity<String> response =
                restTemplate.postForEntity(MINTING_URL, request, String.class);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            return HttpStatus.CREATED;
        }
        return null;
    }
}
