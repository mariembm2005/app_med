package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.dto.LibraryEntryRequestDTO;
import com.example.social_network_for_readers.dto.LibraryEntryResponseDTO;
import com.example.social_network_for_readers.entity.LibraryEntry;
import com.example.social_network_for_readers.mapper.LibraryEntryMapper;
import com.example.social_network_for_readers.service.LibraryEntryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library-entries")
@AllArgsConstructor
public class LibraryEntryController {

    private final LibraryEntryService libraryEntryService;
    private final LibraryEntryMapper libraryEntryMapper;

    @GetMapping
    public List<LibraryEntryResponseDTO> getAllLibraryEntries() {
        return libraryEntryMapper.toResponseEntity(libraryEntryService.getAllLibraryEntries());
    }

    @GetMapping("/{id}")
    public LibraryEntryResponseDTO getLibraryEntry(
            @PathVariable Long id) {

        return libraryEntryMapper.toResponseEntity(libraryEntryService.getLibraryEntry(id));
    }

    @PostMapping
    public LibraryEntryResponseDTO addLibraryEntry(
            @RequestBody LibraryEntryRequestDTO libraryEntry) {

        return libraryEntryMapper.toResponseEntity(libraryEntryService.addLibraryEntry(libraryEntryMapper.toEntity(libraryEntry)));
    }

    @PutMapping("/{id}")
    public LibraryEntryResponseDTO updateLibraryEntry(
            @PathVariable Long id,
            @RequestBody LibraryEntryRequestDTO libraryEntry) {

        return libraryEntryMapper.toResponseEntity(libraryEntryService.updateLibraryEntry(
                id,
                libraryEntryMapper.toEntity(libraryEntry)
        ));
    }

    @DeleteMapping("/{id}")
    public void deleteLibraryEntry(
            @PathVariable Long id) {

        libraryEntryService.deleteLibraryEntry(id);
    }
}
