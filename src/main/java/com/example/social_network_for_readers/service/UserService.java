package com.example.social_network_for_readers.service;

import com.example.social_network_for_readers.entity.*;
import com.example.social_network_for_readers.entity.Collection;
import com.example.social_network_for_readers.mapper.UserMapper;
import com.example.social_network_for_readers.repository.ReviewRepository;
import com.example.social_network_for_readers.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService /*implements UserDetailsService*/ {
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private UserMapper userMapper;




    // GET - Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET - Get one user by name
    public User getUserByName(String username) {
       Optional<User> user = this.userRepository.findById(username);
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
    public User updateUser(String name, User updatedUser) {
        Optional<User> user = userRepository.findById(name);

        if (user.isPresent()) {
            User oldUser = user.get();

            // 1. CRITICAL: Break the relationships from the groups first
            if (oldUser.getGroups() != null) {
                for (Group group : oldUser.getGroups()) {
                    group.getUsers().remove(oldUser);
                }
                oldUser.getGroups().clear();
            }

            // Flush this link removal to the DB right now so the delete works
            userRepository.saveAndFlush(oldUser);

            // 2. Now it is safe to delete the old entity
            userRepository.delete(oldUser);

            // 3. Create new entity with new ID
            User newUser = new User();
            newUser.setUsername(updatedUser.getUsername());
            newUser.setEmail(updatedUser.getEmail());
            newUser.setPassword(updatedUser.getPassword());
            newUser.setImage(updatedUser.getImage());
            newUser.setConfirmPassword(updatedUser.getConfirmPassword());

            // Relink the original groups to the new user if needed
            newUser.setGroups(updatedUser.getGroups());
            newUser.setCollections(updatedUser.getCollections());
            newUser.setReviews(updatedUser.getReviews());

            return userRepository.saveAndFlush(newUser);
        }

        return null;
    }

    // DELETE - Delete a user
    public boolean deleteUser(String name) {
        Optional<User> user = userRepository.findById(name);
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

    public Review writeReview(Work work, String text,User user) {
        Review review = new Review(work, text);
        review.setUser(user);
        user.getReviews().add(review);
        reviewRepository.saveAndFlush(review);
        return review;
    }

    public Collection createCollection(Long id, String name,User user) {
        Collection collection = new Collection(id,name);
        user.getCollections().add(collection);
        return collection;
    }

    public void joinGroup(Group group,User user) {
        user.getGroups().add(group);
    }






}


