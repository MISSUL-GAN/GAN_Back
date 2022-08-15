package gan.missulgan.image.controller;

import static org.springframework.http.MediaType.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gan.missulgan.image.service.ImageService;
import gan.missulgan.image.dto.ImageResponseDTO;
import gan.missulgan.image.exception.UnsupportedMediaException;
import gan.missulgan.member.domain.Member;
import gan.missulgan.member.service.MemberService;
import gan.missulgan.security.auth.AuthDTO;
import gan.missulgan.security.auth.dto.AuthMemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
@Api(tags = "🏞 이미지 API")
public class ImageController {

	private final MemberService memberService;
	private final ImageService imageService;

	@ApiOperation(value = "다중 이미지 업로드", notes = "다중 이미지 업로드. 반환된 `fileName`으로 다시 파일을 조회할 수 있음")
	@PostMapping(path = "uploads", consumes = MULTIPART_FORM_DATA_VALUE)
	public List<ImageResponseDTO> upload(@AuthDTO AuthMemberDTO memberDTO,
		@RequestPart("files") List<MultipartFile> files) {
		return files.stream()
			.map(file -> upload(memberDTO, file))
			.collect(Collectors.toList());
	}

	@ApiOperation(value = "이미지 업로드", notes = "이미지 업로드. 반환된 `fileName`으로 다시 파일을 조회할 수 있음")
	@PostMapping(path = "upload", consumes = MULTIPART_FORM_DATA_VALUE)
	public ImageResponseDTO upload(@AuthDTO AuthMemberDTO memberDTO, @RequestPart("file") MultipartFile multipartFile) {
		try {
			Member member = memberService.getMember(memberDTO.getId());
			InputStream inputStream = multipartFile.getInputStream();
			byte[] bytes = inputStream.readAllBytes();
			String contentType = multipartFile.getContentType();
			return imageService.save(member, bytes, contentType);
		} catch (IOException e) {
			throw new UnsupportedMediaException();
		}
	}

	@ApiOperation(value = "이미지 보기 \uD83D\uDD12❌", notes = "`fileName`으로 파일 조회")
	@GetMapping(value = "{fileName}", produces = {IMAGE_JPEG_VALUE, IMAGE_GIF_VALUE, IMAGE_PNG_VALUE})
	public Resource load(@PathVariable String fileName) {
		return imageService.load(fileName);
	}
}
