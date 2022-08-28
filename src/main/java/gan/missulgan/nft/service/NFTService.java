package gan.missulgan.nft.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.nimbusds.jose.shaded.json.JSONObject;

import gan.missulgan.drawing.domain.Drawing;
import gan.missulgan.drawing.dto.DrawingResponseDTO;
import gan.missulgan.drawing.repository.DrawingRepository;
import gan.missulgan.nft.domain.ChainType;
import gan.missulgan.nft.domain.NFT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class NFTService {

    @Value("${app.nftAuthorizationKey}")
    private String NFT_AUTHORIZATION_KEY;

    private final RestTemplate restTemplate;

    private DrawingRepository drawingRepository;

    private static final String METADATA_IPFS_URL = "https://api.nftport.xyz/v0/metadata";
    private static final String MINTING_URL = "https://api.nftport.xyz/v0/mints/customizable";
    private static final String RETRIEVE_NFT = "https://api.nftport.xyz/v0/mints/";
    private static final String IPFS_PREFIX = "https://ipfs.io/ipfs/";
    private static String CHAIN = ChainType.POLYGON.getValue();
    private static String CONTRACT_ADDRESS = "0x7d08a465f7ffd7371e0d90614b19b35df9553dfb";  // NFT Contract address

	public String metadataToIPFS(String name, String description, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", NFT_AUTHORIZATION_KEY);

        String ipfs = IPFS_PREFIX + fileName;

        // TODO: IPFS 가 접근 가능한지(GET 가능한지? == 유효한 IPFS 인지?) 검증 로직

        JSONObject requestJson = new JSONObject();
        requestJson.put("name", name);
        requestJson.put("description", description);
        requestJson.put("file_url", ipfs);

        HttpEntity<JSONObject> request = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(METADATA_IPFS_URL, request, String.class);
        log.info("NFT metadata response : {}", response);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String body = response.getBody();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(body);
            JsonPrimitive metadataUri = jsonObject.getAsJsonPrimitive("metadata_uri");
            return metadataUri.getAsString();
        }
        return null;
    }

    public void mint(Drawing drawing, String walletAddress) {

        String name = drawing.getTitle();
        String description = drawing.getDescription();
        String fileName = drawing.getImage().getFileName();

        String metadataUri = metadataToIPFS(name, description, fileName);  // TODO: Optional 처리하고 없으면 예외 발생

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", NFT_AUTHORIZATION_KEY);

        JSONObject requestJson = new JSONObject();
        requestJson.put("chain", CHAIN);
        requestJson.put("contract_address", CONTRACT_ADDRESS);
        requestJson.put("metadata_uri", metadataUri);
        requestJson.put("mint_to_address", walletAddress);

        HttpEntity<JSONObject> request = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(MINTING_URL, request, String.class);
        log.info("NFT mint response : {}", response);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String body = response.getBody();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(body);
            String transactionHash = jsonObject.getAsJsonPrimitive("transaction_hash").getAsString();

            saveNFT(drawing, transactionHash);
        }
    }

    public void saveNFT(Drawing drawing, String transactionHash) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", NFT_AUTHORIZATION_KEY);
        HttpEntity request = new HttpEntity(headers);

        URI retrieveNFTUri = URI.create(RETRIEVE_NFT + transactionHash + "?chain=" + CHAIN);

        ResponseEntity<String> response =
            restTemplate.exchange(retrieveNFTUri, HttpMethod.GET, request, String.class);
        log.info("NFT information response : {}", response);

        if (response.getStatusCode().equals(HttpStatus.OK)) {
            String body = response.getBody();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(body);
            String contractAddress = jsonObject.getAsJsonPrimitive("contract_address").getAsString();
            String tokenId = jsonObject.getAsJsonPrimitive("token_id").getAsString();

            drawing.setNFT(new NFT(contractAddress, tokenId));
            drawingRepository.save(drawing);
        }
    }
}
