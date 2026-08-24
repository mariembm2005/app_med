package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.entity.LibraryEntry;
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

    @GetMapping
    public List<LibraryEntry> getAllLibraryEntries() {
        return libraryEntryService.getAllLibraryEntries();
    }

    @GetMapping("/{id}")
    public LibraryEntry getLibraryEntry(
            @PathVariable Long id) {

        return libraryEntryService.getLibraryEntry(id);
    }

    @PostMapping
    public LibraryEntry addLibraryEntry(
            @RequestBody LibraryEntry libraryEntry) {

        return libraryEntryService.addLibraryEntry(libraryEntry);
    }

    @PutMapping("/{id}")
    public LibraryEntry updateLibraryEntry(
            @PathVariable Long id,
            @RequestBody LibraryEntry libraryEntry) {

        return libraryEntryService.updateLibraryEntry(
                id,
                libraryEntry
        );
    }

    @DeleteMapping("/{id}")
    public void deleteLibraryEntry(
            @PathVariable Long id) {

        libraryEntryService.deleteLibraryEntry(id);
    }
}
