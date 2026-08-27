package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.dto.CollectionRequestDTO;
import com.example.social_network_for_readers.dto.CollectionResponseDTO;
import com.example.social_network_for_readers.entity.Collection;
import com.example.social_network_for_readers.mapper.CollectionMapper;
import com.example.social_network_for_readers.service.CollectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@AllArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;
    private final CollectionMapper collectionMapper;

    @GetMapping
    public List<CollectionResponseDTO> getAllCollections() {
        return collectionMapper.toDTO(collectionService.getAllCollections());
    }

    @GetMapping("/{id}")
    public CollectionResponseDTO getCollection(@PathVariable int id) {
        return collectionMapper.toDTO(collectionService.getCollection(id));
    }

    @PostMapping
    public CollectionResponseDTO addCollection(@RequestBody CollectionRequestDTO collection) {
        return collectionMapper.toDTO(collectionService.addCollection(collectionMapper.toEntity(collection)));
    }

    @PutMapping("/{id}")
    public CollectionResponseDTO updateCollection(
            @PathVariable int id,
            @RequestBody CollectionRequestDTO collection) {

        return collectionMapper.toDTO(collectionService.updateCollection(id, collectionMapper.toEntity(collection)));
    }

    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable int id) {
        collectionService.deleteCollection(id);
    }
}
