package com.example.social_network_for_readers.Controller;


import com.example.social_network_for_readers.entity.Group;
import com.example.social_network_for_readers.entity.User;
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
    @GetMapping
    public List<Group> groups(){
        return groupService.getAllGroups();
    }
    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable Long id){
        return groupService.getGroupById(id);
    }
    @DeleteMapping("/{id}")
    public boolean deleteGroup(@PathVariable Long id){
        return groupService.deleteGroup(id);
    }
    @PostMapping
    public ResponseEntity<Group> addGroup(@RequestBody Group group){
        Group gr = groupService.addGroup(group);
        return ResponseEntity.status(201).body(gr);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@RequestBody Group group, @PathVariable Long id){
        id = group.getId();
        Group gr = groupService.updateGroup(group,id);
        return ResponseEntity.status(201).body(gr);
    }

    @PostMapping("/{id}/{username}")
    public ResponseEntity<Group> addUser(
            @PathVariable Long id,
            @PathVariable String username) {

        Group group = groupService.getGroupById(id);
        User user = userService.getUserByName(username);

        groupService.addMember(user, group);
        return ResponseEntity.status(201).body(group);
    }
}
