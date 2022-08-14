package gan.missulgan.tag.service;

import gan.missulgan.tag.domain.Tag;
import gan.missulgan.tag.dto.TagResponseDTO;
import gan.missulgan.tag.exception.BadTagException;
import gan.missulgan.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<TagResponseDTO> getAllTags() {
        return tagRepository.findAll()
                .stream()
                .map(TagResponseDTO::from)
                .collect(Collectors.toList());
    }

    public Set<Tag> getTagsByIds(Set<Long> ids) {
        return ids.stream()
                .map(this::getTagById)
                .collect(Collectors.toSet());
    }

    private Tag getTagById(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(BadTagException::new);
    }
}
