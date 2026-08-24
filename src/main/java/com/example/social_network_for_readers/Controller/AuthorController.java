package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.entity.Author;
import com.example.social_network_for_readers.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public Author addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @PutMapping("/{id}")
    public Author updateAuthor(
            @PathVariable Long id,
            @RequestBody Author author) {

        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
