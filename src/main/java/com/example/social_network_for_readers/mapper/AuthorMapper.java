package com.example.social_network_for_readers.mapper;


import com.example.social_network_for_readers.dto.AuthorRequestDTO;
import com.example.social_network_for_readers.dto.AuthorResponseDTO;
import com.example.social_network_for_readers.entity.Author;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class AuthorMapper {
    public Author toEntity(AuthorRequestDTO authorRequestDTO) {
        Author author = new Author();
        author.setId(authorRequestDTO.getId());
        author.setName(authorRequestDTO.getName());
        return author;
    }

    public AuthorResponseDTO toDTO(Author author) {
        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO();
        authorResponseDTO.setId(author.getId());
        authorResponseDTO.setName(author.getName());
        return authorResponseDTO;
    }
    public List<AuthorResponseDTO> toDTO(List<Author> authors) {
        return authors.stream()
                .map(this::toDTO)
                .collect(Collectors.<AuthorResponseDTO>toList());
    }
}
