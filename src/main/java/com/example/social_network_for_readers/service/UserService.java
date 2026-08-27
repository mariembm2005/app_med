package com.example.social_network_for_readers.service;

import com.example.social_network_for_readers.entity.*;
import com.example.social_network_for_readers.entity.Collection;
import com.example.social_network_for_readers.mapper.Reviewmapper;
import com.example.social_network_for_readers.mapper.UserMapper;
import com.example.social_network_for_readers.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService /*implements UserDetailsService*/ {
    private final CollectionService collectionService;
    private final CollectionRepository collectionRepository;
    private final Reviewmapper reviewmapper;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private WorkRepository workRepository;
    private UserMapper userMapper;
    private LibraryEntryRepository libraryEntryRepository;




    // GET - Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET - Get one user by name
    public User getUserByID(Long id) {
       Optional<User> user = this.userRepository.findById(id);
       return user.orElse(null);

    }
/*
     public UserResponseDTO createUser(UserRequestDTO userdtotmp) {
        UserEntity user = userMapper.toEntity(userdtotmp);
        userRepository.save(user);
        return userMapper.toResponseDto(user);
    }

* */

    // PUT - Update a user
    @Transactional
    public User updateUser(Long id, User updatedUser) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setConfirmPassword(updatedUser.getConfirmPassword());
        user.setImage(updatedUser.getImage());

        return user;
    }

    // DELETE - Delete a user
    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            userRepository.flush();
            return true;
        }
        return false;

    }
    // POST - Add a user
    public User createUser(User user){
        return this.userRepository.saveAndFlush(user);
    }

    public LibraryEntry addToCatalog(Work work, Format format,User user) {
        LibraryEntry entry = new LibraryEntry(work, format);

        user.getEntries().add(entry);
        return entry;
    }

    public Review writeReview(Long wid, Review review,Long id) {
        Optional<User> us= userRepository.findById(id);
        if (us.isPresent()) {
            User user = us.get();
            review.setUser(user);
            Work work = workRepository.findById(wid)
                    .orElseThrow(() -> new RuntimeException("Work not found"));
            review.setWork(work);
            review.setDateCreated(LocalDate.now());
            user.getReviews().add(review);
            reviewRepository.saveAndFlush(review);
            return review;
        }
        return null;

    }

    public Collection createCollection(Long id, Collection collection) {
        Optional<User> us= userRepository.findById(id);
        if (us.isPresent()) {
            User user = us.get();
            Collection col = new Collection();
            col.setName(collection.getName());
            col.setUser(user);
            return collectionRepository.saveAndFlush(col);

        }
        return null;

    }
    public LibraryEntry addToCatalog(Long id,LibraryEntry l,Long wid){
        Optional<User> us= userRepository.findById(id);
        Work work = workRepository.findById(wid).orElse(null);
        if (us.isPresent()) {
            User user = us.get();
            LibraryEntry le = new LibraryEntry();
            le.setUser(us.get());
            le.setDateAdded(LocalDate.now());
            le.setFormat(l.getFormat());
            le.setRating(l.getRating());
            le.setImage(l.getImage());
            le.setWork(work);
            le.setUser(user);
            le.setTitle(l.getTitle());
            return libraryEntryRepository.saveAndFlush(le);

        }
        return null;
    }

    public void joinGroup(Group group,User user) {
        user.getGroups().add(group);
    }






}


