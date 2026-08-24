package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.AuthorResponseDTO;
import com.example.social_network_for_readers.dto.WorkRequestDTO;
import com.example.social_network_for_readers.dto.WorkResponseDTO;
import com.example.social_network_for_readers.entity.Author;
import com.example.social_network_for_readers.entity.Work;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class WorkMapper {
    public Work toEntity(WorkRequestDTO workRequestDTO) {
        Work work = new Work();
        work.setId(workRequestDTO.getId());
        work.setTitle(workRequestDTO.getTitle());
        work.setOriginalPubYear(workRequestDTO.getOriginalPubYear());
        work.setImage(workRequestDTO.getImage());
        List<Author> authors = workRequestDTO.getAuthorRequestDTOList()
                .stream()
                .map(author->new Author(author.getId(), author.getName()))
                .collect(Collectors.toUnmodifiableList());
        work.setAuthors(authors);
        return work;
    }
    public WorkResponseDTO toDTO(Work work) {
        WorkResponseDTO workResponseDTO = new WorkResponseDTO();
        workResponseDTO.setId(work.getId());
        workResponseDTO.setTitle(work.getTitle());
        workResponseDTO.setOriginalPubYear(work.getOriginalPubYear());
        workResponseDTO.setImage(work.getImage());
        List<AuthorResponseDTO> authors = work.getAuthors()
                .stream()
                .map(authorResp->new AuthorResponseDTO(authorResp.getId(), authorResp.getName()))
                .collect(Collectors.toList());
        workResponseDTO.setAuthorResponseDTOList(authors);
        return workResponseDTO;
    }
}
