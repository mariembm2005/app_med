package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.CollectionRequestDTO;
import com.example.social_network_for_readers.dto.CollectionResponseDTO;
import com.example.social_network_for_readers.dto.UserRequestDTO;
import com.example.social_network_for_readers.dto.UserResponseDTO;
import com.example.social_network_for_readers.entity.Collection;
import com.example.social_network_for_readers.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO userRequestDTO) {
        User userEntitytmp = new User();
        updateEntity(userEntitytmp,userRequestDTO);
        return userEntitytmp;
    }


    public UserResponseDTO toResponseDto(User userEntity) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUsername(userEntity.getUsername());
        userResponseDTO.setEmail(userEntity.getEmail());
        userResponseDTO.setImage(userEntity.getImage());
        List<CollectionResponseDTO> collectionResponseDTOList = userEntity.getCollections()
                .stream()
                .map(collection -> new CollectionResponseDTO(collection.getId(), collection.getName()))
                .collect(Collectors.toList());
                ;
        userResponseDTO.setCollectionResponseDTOList(collectionResponseDTOList);
        return userResponseDTO;
    }


    private void updateEntity(User userEntitytmp, UserRequestDTO userRequestDTO) {
        userEntitytmp.setEmail(userRequestDTO.getEmail());
        userEntitytmp.setPassword(userRequestDTO.getPassword());
        userEntitytmp.setUsername(userRequestDTO.getUsername());
        userEntitytmp.setConfirmPassword(userRequestDTO.getConfirmPassword());
        List<Collection> dtoList = userRequestDTO.getCollectionRequestDTOList().stream()
                .map(col -> new Collection(col.getId(), col.getName()))
                .collect(Collectors.toList());

        userEntitytmp.setCollections(dtoList);
    }

}
