package gan.missulgan.image.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gan.missulgan.image.domain.ImageService;
import gan.missulgan.image.dto.SavedImageDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

	@PostMapping("upload")
	public List<SavedImageDTO> upload(@RequestParam("files") List<MultipartFile> files) {
		return imageService.save(files);
	}

}
