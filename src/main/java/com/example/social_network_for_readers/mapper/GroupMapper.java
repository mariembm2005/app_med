package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.GroupRequestDTO;
import com.example.social_network_for_readers.dto.GroupResponseDTO;
import com.example.social_network_for_readers.entity.Group;

public class GroupMapper {

    public Group toEntity(GroupRequestDTO groupRequestDTO) {
        Group group = new Group();
        group.setId(groupRequestDTO.getId());
        group.setName(groupRequestDTO.getName());
        group.setDescription(groupRequestDTO.getDescription());
        return group;
    }
    public GroupResponseDTO toResponseDTO(Group group) {
        GroupResponseDTO groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setId(group.getId());
        groupResponseDTO.setName(group.getName());
        groupResponseDTO.setDescription(group.getDescription());
        return groupResponseDTO;
    }
}
