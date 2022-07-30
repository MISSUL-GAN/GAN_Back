package gan.missulgan.image.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gan.missulgan.image.domain.ImageService;
import gan.missulgan.image.dto.SavedImageDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	@ApiOperation(value = "이미지 업로드", notes = "이미지 업로드. 반환된 `fileName`으로 다시 파일을 조회할 수 있음")
	@PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<SavedImageDTO> upload(@RequestPart("files") List<MultipartFile> files) {
		return imageService.save(files);
	}

	@ApiOperation(value = "이미지 보기", notes = "업로드 시나 다른 곳에서 조회된 `fileName`으로 파일 조회")
	@GetMapping(value = "{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE,
		MediaType.IMAGE_PNG_VALUE})
	public Resource load(@PathVariable String fileName) {
		Resource resource = imageService.load(fileName);
		return resource;
	}
}
