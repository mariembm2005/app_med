package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.dto.AuthorRequestDTO;
import com.example.social_network_for_readers.dto.AuthorResponseDTO;
import com.example.social_network_for_readers.entity.Author;
import com.example.social_network_for_readers.mapper.AuthorMapper;
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
    private final AuthorMapper authorMapper;


    @GetMapping
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorMapper.toDTO(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public AuthorResponseDTO getAuthor(@PathVariable Long id) {
        return authorMapper.toDTO(authorService.getAuthorById(id));
    }

    @PostMapping
    public AuthorResponseDTO addAuthor(@RequestBody AuthorRequestDTO author) {
        return authorMapper.toDTO(authorService.addAuthor(authorMapper.toEntity(author)));
    }

    @PutMapping("/{id}")
    public AuthorResponseDTO updateAuthor(
            @PathVariable Long id,
            @RequestBody AuthorRequestDTO author) {

        return authorMapper.toDTO(authorService.updateAuthor(id, authorMapper.toEntity(author)));
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
