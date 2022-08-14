package gan.missulgan.tag.controller;

import gan.missulgan.tag.dto.TagResponseDTO;
import gan.missulgan.tag.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("tag")
public class TagController {

    private final TagService tagService;

    @GetMapping("all")
    @ApiOperation(value = "모든 태그 가져오기", notes = "존재하는 모든 태그를 가져옵니다. `AccessToken` 불필요")
    public List<TagResponseDTO> getTags() {
        return tagService.getAllTags();
    }
}
