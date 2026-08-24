package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.TagRequestDTO;
import com.example.social_network_for_readers.dto.TagResponseDTO;
import com.example.social_network_for_readers.entity.Tag;
import lombok.*;


@Data

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
}
