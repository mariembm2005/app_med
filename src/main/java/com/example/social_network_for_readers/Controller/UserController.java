package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.dto.*;
import com.example.social_network_for_readers.entity.*;
import com.example.social_network_for_readers.mapper.*;
import com.example.social_network_for_readers.service.GroupService;
import com.example.social_network_for_readers.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.catalog.Catalog;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor

public class UserController {

    private final UserService userService;
    private final GroupService groupService;
    private final UserMapper userMapper;
    private final WorkMapper workMapper;
    @Autowired
    private final Reviewmapper reviewmapper;
    @Autowired
    private final GroupMapper groupMapper;
    @Autowired
    private final CollectionMapper collectionMapper;
    @Autowired
    private LibraryEntryMapper libraryEntryMapper;


    // GET /users
    @GetMapping
    public List<UserResponseDTO> getAllClients() {
        return userMapper.toResponseDtoLis(userService.getAllUsers());
    }

    // GET /users/Ali
    @GetMapping("/{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return userMapper.toResponseDto(userService.getUserByID(id));
    }

    // POST /users
    @PostMapping
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody UserRequestDTO user) {
        UserResponseDTO CreateUser =  userMapper.toResponseDto(userService.createUser(userMapper.toEntity(user)));
        return ResponseEntity.status(201).body(CreateUser);  //201 : ok reussi
    }
    @PostMapping("/groups/{grpId}/user/{userId}")
    public void joinGroup(
            @PathVariable Long grpId,
            @PathVariable Long userId) {

        groupService.addMember(grpId, userId);
    }

    // PUT /users/Ali
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO user) {

        return userMapper.toResponseDto(userService.updateUser(id, userMapper.toEntity(user)));
    }

    // DELETE /users/Ali
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        boolean deleted = userService.deleteUser(id);

        if (deleted) {
            return "User deleted successfully";
        }

        return "User not found";


    }
    @PostMapping("/{id}/work/{wid}/reviews")
    public ReviewResponseDTO writeReview(@PathVariable Long id, @RequestBody ReviewRequestDTO review,@PathVariable Long wid) {

            return reviewmapper.toReviewResponseDTO(userService.writeReview(wid,reviewmapper.toEntity(review),id));
    }

    @PostMapping("/{id}/collection")
    public CollectionResponseDTO addToCollection(@PathVariable Long id, @RequestBody CollectionRequestDTO collection) {

        return collectionMapper.toDTO(userService.createCollection(id,collectionMapper.toEntity(collection)));
}
    @PostMapping("/{id}/library-entry/work/{wid}")
    public LibraryEntryResponseDTO addToCatalog(@PathVariable Long id, @RequestBody LibraryEntryRequestDTO le,@PathVariable Long wid) {

        return libraryEntryMapper.toResponseEntity(userService.addToCatalog(id,libraryEntryMapper.toEntity(le),wid));
}}
