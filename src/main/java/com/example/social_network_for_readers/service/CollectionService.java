package com.example.social_network_for_readers.service;

import com.example.social_network_for_readers.entity.*;
import com.example.social_network_for_readers.entity.Collection;
import com.example.social_network_for_readers.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;

    public List<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }

    public Collection getCollection(int id) {
        Optional<Collection> collection = collectionRepository.findById(id);

        if (collection.isPresent()) {
            return collection.get();
        }

        return null;
    }

    public Collection addCollection(Collection collection) {
        return collectionRepository.saveAndFlush(collection);
    }

    public Collection updateCollection(int id, Collection updatedCollection) {

        Optional<Collection> collection =
                collectionRepository.findById(id);

        if (collection.isPresent()) {

            Collection c = collection.get();

            c.setName(updatedCollection.getName());

            return collectionRepository.saveAndFlush(c);
        }

        return null;
    }

    public void deleteCollection(int id) {

        Optional<Collection> collection =
                collectionRepository.findById(id);

        if (collection.isPresent()) {

            Collection c = collection.get();

            collectionRepository.delete(c);
            collectionRepository.flush();
        }
    }
}
