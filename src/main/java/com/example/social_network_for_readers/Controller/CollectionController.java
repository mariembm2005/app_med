package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.entity.Collection;
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

    @GetMapping
    public List<Collection> getAllCollections() {
        return collectionService.getAllCollections();
    }

    @GetMapping("/{id}")
    public Collection getCollection(@PathVariable int id) {
        return collectionService.getCollection(id);
    }

    @PostMapping
    public Collection addCollection(@RequestBody Collection collection) {
        return collectionService.addCollection(collection);
    }

    @PutMapping("/{id}")
    public Collection updateCollection(
            @PathVariable int id,
            @RequestBody Collection collection) {

        return collectionService.updateCollection(id, collection);
    }

    @DeleteMapping("/{id}")
    public void deleteCollection(@PathVariable int id) {
        collectionService.deleteCollection(id);
    }
}
