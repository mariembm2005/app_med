package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.LibraryEntryRequestDTO;
import com.example.social_network_for_readers.dto.LibraryEntryResponseDTO;
import com.example.social_network_for_readers.entity.LibraryEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LibraryEntryMapper {

    private final UserMapper userMapper;
    private final WorkMapper workMapper;

    public LibraryEntryMapper(UserMapper userMapper, WorkMapper workMapper) {
        this.userMapper = userMapper;
        this.workMapper = workMapper;
    }

    public LibraryEntry toEntity(LibraryEntryRequestDTO libraryEntry) {
        LibraryEntry le = new LibraryEntry();
        le.setId(libraryEntry.getId());
        le.setDateAdded(libraryEntry.getDateAdded());
        le.setRating(libraryEntry.getRating());
        le.setFormat(libraryEntry.getFormat());
        le.setImage(libraryEntry.getImage());
        le.setTitle(libraryEntry.getTitle());
        return le;

    }
    public LibraryEntryResponseDTO toResponseEntity(LibraryEntry libraryEntry) {
        LibraryEntryResponseDTO dto = new LibraryEntryResponseDTO();
        dto.setId(libraryEntry.getId());
        dto.setDateAdded(libraryEntry.getDateAdded());
        dto.setRating(libraryEntry.getRating());
        dto.setFormat(libraryEntry.getFormat());
        dto.setImage(libraryEntry.getImage());
        dto.setTitle(libraryEntry.getTitle());
        dto.setUserResponseDTO(userMapper.toResponseDto(libraryEntry.getUser()));
        dto.setWorkResponseDTOList(workMapper.toDTO(libraryEntry.getWork()));
        return dto;
    }
    public List<LibraryEntryResponseDTO> toResponseEntity(List<LibraryEntry> libraryEntries) {
        return libraryEntries.stream()
                .map(this::toResponseEntity)
                .collect(Collectors.<LibraryEntryResponseDTO>toList());

    }
}
