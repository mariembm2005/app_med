package com.example.social_network_for_readers.Controller;

import com.example.social_network_for_readers.entity.User;
import com.example.social_network_for_readers.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor

public class UserController {

    private final UserService userService;



    // GET /users
    @GetMapping
    public List<User> getAllClients() {
        return userService.getAllUsers();
    }

    // GET /users/Ali
    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.getUserByName(username);
    }

    // POST /users
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User CreateUser =  userService.createUser(user);
        return ResponseEntity.status(201).body(CreateUser);  //201 : ok reussi
    }

    // PUT /users/Ali
    @PutMapping("/{name}")
    public User updateUser(
            @PathVariable String name,
            @RequestBody User user) {

        return userService.updateUser(name, user);
    }

    // DELETE /users/Ali
    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {

        boolean deleted = userService.deleteUser(username);

        if (deleted) {
            return "User deleted successfully";
        }

        return "User not found";
    }}
