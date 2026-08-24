package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.entity.Tag;
import com.example.social_network_for_readers.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;


    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public Tag getTag(@PathVariable Long id) {
        return tagService.getTag(id);
    }

    @PostMapping
    public Tag addTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    @PutMapping("/{id}")
    public Tag updateTag(
            @PathVariable Long id,
            @RequestBody Tag tag) {

        return tagService.updateTag(id, tag);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
