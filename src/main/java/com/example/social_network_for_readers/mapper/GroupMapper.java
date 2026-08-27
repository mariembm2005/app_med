package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.GroupRequestDTO;
import com.example.social_network_for_readers.dto.GroupResponseDTO;
import com.example.social_network_for_readers.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupMapper {

    private final UserMapper userMapper;

    public GroupMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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
        groupResponseDTO.setMembers(userMapper.toResponseDtoLis(group.getUsers()));
        return groupResponseDTO;
    }
    public List<GroupResponseDTO> toResponseDTO(List<Group> groups) {
        return groups.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.<GroupResponseDTO>toList());
    }
}
