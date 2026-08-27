package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.dto.TagRequestDTO;
import com.example.social_network_for_readers.dto.TagResponseDTO;
import com.example.social_network_for_readers.entity.Tag;
import com.example.social_network_for_readers.mapper.TagMapper;
import com.example.social_network_for_readers.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public List<TagResponseDTO> getAllTags() {
        return tagMapper.toDto(tagService.getAllTags());
    }

    @GetMapping("/{id}")
    public TagResponseDTO getTag(@PathVariable Long id) {
        return tagMapper.toDto(tagService.getTag(id));
    }

    @PostMapping
    public TagResponseDTO addTag(@RequestBody TagRequestDTO tag) {
        return tagMapper.toDto(tagService.addTag(tagMapper.toEntity(tag)));
    }

    @PutMapping("/{id}")
    public TagResponseDTO updateTag(
            @PathVariable Long id,
            @RequestBody TagRequestDTO tag) {

        return tagMapper.toDto(tagService.updateTag(id, tagMapper.toEntity(tag)));
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
