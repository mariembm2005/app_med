package com.example.app_medical.Controller;

import com.example.app_medical.dto.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor

public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;




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
        UserResponseDTO CreateUser = userMapper.toResponseDto(userService.createUser(userMapper.toEntity(user)));
        return ResponseEntity.status(201).body(CreateUser);  //201 : ok reussi
    }




    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO user) {

        return userMapper.toResponseDto(userService.updateUser(id, userMapper.toEntity(user)));
    }


    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        boolean deleted = userService.deleteUser(id);

        if (deleted) {
            return "User deleted successfully";
        }

        return "User not found";


    }
}
