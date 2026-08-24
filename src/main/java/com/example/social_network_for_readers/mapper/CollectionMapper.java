package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.CollectionRequestDTO;
import com.example.social_network_for_readers.dto.CollectionResponseDTO;
import com.example.social_network_for_readers.entity.Collection;

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
}
