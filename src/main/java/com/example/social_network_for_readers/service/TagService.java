package com.example.social_network_for_readers.service;

import com.example.social_network_for_readers.entity.Tag;
import com.example.social_network_for_readers.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTag(Long id) {
        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isPresent()) {
            return tag.get();
        }

        return null;
    }

    public Tag addTag(Tag tag) {
        return tagRepository.saveAndFlush(tag);
    }

    public Tag updateTag(Long id, Tag updatedTag) {

        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isPresent()) {

            Tag t = tag.get();

            t.setName(updatedTag.getName());

            return tagRepository.saveAndFlush(t);
        }

        return null;
    }

    public void deleteTag(Long id) {

        Optional<Tag> tag = tagRepository.findById(id);

        if (tag.isPresent()) {

            Tag t = tag.get();

            tagRepository.delete(t);
            tagRepository.flush();
        }
    }
}
