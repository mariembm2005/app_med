package com.example.social_network_for_readers.Controller;


import com.example.social_network_for_readers.dto.GroupRequestDTO;
import com.example.social_network_for_readers.dto.GroupResponseDTO;
import com.example.social_network_for_readers.dto.UserRequestDTO;
import com.example.social_network_for_readers.dto.UserResponseDTO;
import com.example.social_network_for_readers.entity.Group;
import com.example.social_network_for_readers.entity.User;
import com.example.social_network_for_readers.mapper.GroupMapper;
import com.example.social_network_for_readers.mapper.UserMapper;
import com.example.social_network_for_readers.repository.GroupRepository;
import com.example.social_network_for_readers.service.GroupService;
import com.example.social_network_for_readers.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor

public class GroupController {
    private final GroupService groupService;
    private final UserService userService;
    private final GroupMapper groupMapper;
    private final UserMapper userMapper;
    @GetMapping
    public List<GroupResponseDTO> groups(){
        return groupMapper.toResponseDTO(groupService.getAllGroups());
    }
    @GetMapping("/{id}")
    public GroupResponseDTO getGroupById(@PathVariable Long id){
        return groupMapper.toResponseDTO(groupService.getGroupById(id));
    }
    @DeleteMapping("/{id}")
    public boolean deleteGroup(@PathVariable Long id){
        return groupService.deleteGroup(id);
    }
    @PostMapping
    public ResponseEntity<GroupResponseDTO> addGroup(@RequestBody GroupRequestDTO group){
        Group gr = groupService.addGroup(groupMapper.toEntity(group));
        return ResponseEntity.status(201).body(groupMapper.toResponseDTO(gr));

    }
    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> updateGroup(@RequestBody GroupRequestDTO group, @PathVariable Long id){
        id = group.getId();
        Group gr = groupService.updateGroup(groupMapper.toEntity(group),id);
        return ResponseEntity.status(201).body(groupMapper.toResponseDTO(gr));
    }

    @PostMapping("/{id}/{user_Id}")
    public ResponseEntity<GroupResponseDTO> addUser(
            @PathVariable Long id,
            @PathVariable Long user_Id) {

        Group group = groupService.getGroupById(id);
        User user = userService.getUserByID(user_Id);

        groupService.addMember(id, user_Id);
        return ResponseEntity.status(201).body(groupMapper.toResponseDTO(group));
    }
    @DeleteMapping("/{id}/{user_Id}")
    public ResponseEntity<GroupResponseDTO> deleteUser(
            @PathVariable Long id,
            @PathVariable Long user_Id) {

        Group group = groupService.getGroupById(id);
        User user = userService.getUserByID(user_Id);

        groupService.removeMember(user, group);
        return ResponseEntity.status(201).body(groupMapper.toResponseDTO(group));
    }
    @GetMapping("/{id}/members")
    public List<UserResponseDTO> getMembers(@PathVariable Long id) {

        List<UserResponseDTO> users =this.groupMapper.toResponseDTO(this.groupService.getGroupById(id)).getMembers();
        System.out.println(users);
        return users;
    }
}
