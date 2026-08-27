package com.example.social_network_for_readers.service;

import com.example.social_network_for_readers.dto.LibraryEntryRequestDTO;
import com.example.social_network_for_readers.entity.Collection;
import com.example.social_network_for_readers.entity.LibraryEntry;
import com.example.social_network_for_readers.entity.Tag;
import com.example.social_network_for_readers.repository.LibraryEntryRepository;
import com.example.social_network_for_readers.repository.TagRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LibraryEntryService {
    private LibraryEntryRepository libraryEntryRepository;


    public void addTag(Tag t, Long LeID){
        Optional<LibraryEntry> le = libraryEntryRepository.findById(LeID);
        if(le.isPresent()){
            le.get().getTags().add(t);
            libraryEntryRepository.saveAndFlush(le.get());
        }


    }
    public void removeTag(Tag t, Long LeID){
        Optional<LibraryEntry> le = libraryEntryRepository.findById(LeID);
        if(le.isPresent()){
            le.get().getTags().remove(t);
            libraryEntryRepository.saveAndFlush(le.get());
        }


    }




    public void addToCollection(Collection collection,Long LeID) {
        Optional<LibraryEntry> le = libraryEntryRepository.findById(LeID);
        if(le.isPresent()){
            le.get().getCollections().add(collection);
            libraryEntryRepository.saveAndFlush(le.get());
        }


    }

    public void removeFromCollection(Collection collection,Long LeID) {
        Optional<LibraryEntry> le = libraryEntryRepository.findById(LeID);
        if(le.isPresent()){
            le.get().getCollections().remove(collection);
            libraryEntryRepository.delete(le.get());
            libraryEntryRepository.saveAndFlush(le.get());
        }
    }

    public void updateRating(int rating, Long LeID) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        else{
        Optional<LibraryEntry> le = libraryEntryRepository.findById(LeID);
        if(le.isPresent()){
            LibraryEntry entry = le.get();
            entry.setRating(rating);
            libraryEntryRepository.saveAndFlush(entry);

        }}

    }

        public List<LibraryEntry> getAllLibraryEntries() {
            return libraryEntryRepository.findAll();
        }

        public LibraryEntry getLibraryEntry(Long id) {

            Optional<LibraryEntry> entry =
                    libraryEntryRepository.findById(id);

            if (entry.isPresent()) {
                return entry.get();
            }

            return null;
        }

        public LibraryEntry addLibraryEntry(LibraryEntry libraryEntry) {
            return libraryEntryRepository.saveAndFlush(libraryEntry);
        }

        public LibraryEntry updateLibraryEntry(
                Long id,
                LibraryEntry updatedEntry) {

            Optional<LibraryEntry> entry =
                    libraryEntryRepository.findById(id);

            if (entry.isPresent()) {

                LibraryEntry le = entry.get();

                le.setDateAdded(updatedEntry.getDateAdded());
                le.setFormat(updatedEntry.getFormat());
                le.setTitle(updatedEntry.getTitle());
                le.setImage(updatedEntry.getImage());
                le.setRating(updatedEntry.getRating());

                le.setUser(updatedEntry.getUser());
                le.setWork(updatedEntry.getWork());

                le.setTags(updatedEntry.getTags());
                le.setCollections(updatedEntry.getCollections());

                return libraryEntryRepository.saveAndFlush(le);
            }

            return null;
        }

        public void deleteLibraryEntry(Long id) {

            Optional<LibraryEntry> entry =
                    libraryEntryRepository.findById(id);

            if (entry.isPresent()) {

                LibraryEntry le = entry.get();

                libraryEntryRepository.delete(le);
                libraryEntryRepository.flush();
            }
        }


}


