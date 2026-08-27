package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.TagRequestDTO;
import com.example.social_network_for_readers.dto.TagResponseDTO;
import com.example.social_network_for_readers.entity.Tag;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Component
public class TagMapper {
    public Tag toEntity(TagRequestDTO dto) {
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setName(dto.getName());
        return tag;
    }

    public TagResponseDTO toDto(Tag tag) {
        TagResponseDTO tagResponseDTO = new TagResponseDTO();
        tagResponseDTO.setId(tag.getId());
        tagResponseDTO.setName(tag.getName());
        return tagResponseDTO;
    }
    public List<TagResponseDTO> toDto(List<Tag> tags) {
        List<TagResponseDTO> tagResponseDTOs = new ArrayList<>();
        return tags.stream()
                .map(this::toDto)
                .collect(Collectors.<TagResponseDTO>toList());
    }
}
