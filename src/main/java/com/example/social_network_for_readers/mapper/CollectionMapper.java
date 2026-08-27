package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.CollectionRequestDTO;
import com.example.social_network_for_readers.dto.CollectionResponseDTO;
import com.example.social_network_for_readers.entity.Collection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component

public class CollectionMapper {
    public Collection toEntity(CollectionRequestDTO requestDTO) {
        Collection collection = new Collection();
        collection.setId(requestDTO.getId());
        collection.setName(requestDTO.getName());
        return collection;
    }
    public CollectionResponseDTO toDTO(Collection collection) {
        CollectionResponseDTO collectionResponseDTO = new CollectionResponseDTO();
        collectionResponseDTO.setId(collection.getId());
        collectionResponseDTO.setName(collection.getName());
        return collectionResponseDTO;
    }
    public List<CollectionResponseDTO> toDTO(List<Collection> collections) {
        return collections.stream()
                .map(this::toDTO)
                .collect(Collectors.<CollectionResponseDTO>toList());
    }
}
