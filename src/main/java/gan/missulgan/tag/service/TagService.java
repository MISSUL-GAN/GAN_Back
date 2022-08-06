package gan.missulgan.tag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import gan.missulgan.tag.dto.TagResponseDTO;
import gan.missulgan.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;

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
}
