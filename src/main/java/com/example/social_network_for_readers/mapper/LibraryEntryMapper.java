package com.example.social_network_for_readers.mapper;

import com.example.social_network_for_readers.dto.LibraryEntryResponseDTO;
import com.example.social_network_for_readers.entity.LibraryEntry;
import org.springframework.http.ResponseEntity;

public class LibraryEntryMapper {

    public LibraryEntry toEntity(LibraryEntry libraryEntry) {
        LibraryEntry le = new LibraryEntry();
        le.setId(libraryEntry.getId());
        le.setDateAdded(libraryEntry.getDateAdded());
        le.setRating(libraryEntry.getRating());
        le.setFormat(libraryEntry.getFormat());
        le.setImage(libraryEntry.getImage());
        return le;

    }
    public LibraryEntryResponseDTO toResponseEntity(LibraryEntry libraryEntry) {
        LibraryEntryResponseDTO dto = new LibraryEntryResponseDTO();
        dto.setId(libraryEntry.getId());
        dto.setDateAdded(libraryEntry.getDateAdded());
        dto.setRating(libraryEntry.getRating());
        dto.setFormat(libraryEntry.getFormat());
        dto.setImage(libraryEntry.getImage());
        return dto;
    }
}
