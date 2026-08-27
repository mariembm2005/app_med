package com.example.social_network_for_readers.service;

import com.example.social_network_for_readers.entity.Group;
import com.example.social_network_for_readers.entity.User;
import com.example.social_network_for_readers.repository.GroupRepository;
import com.example.social_network_for_readers.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    @Transactional
    public void addMember(Long grpId, Long userId) {

        Group group = groupRepository.findById(grpId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        group.getUsers().add(user);
        groupRepository.save(group);
    }
    public void removeMember(User user, Group group){
        group.getUsers().remove(user);
        groupRepository.saveAndFlush(group);
    }
    public boolean isMember(User user, Group group){
        return group.getUsers().contains(user);
    }
    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }
    public List<User> getAllUsers(Long id){
        Optional<Group> group = groupRepository.findById(id);
        if(group.isPresent()){
            return group.get().getUsers();
        }
            return null;
    }
    public Group getGroupById(Long id){
        Optional<Group> gr =  groupRepository.findById(id);
        return gr.orElse(null);
    }
    public boolean deleteGroup(Long id){
        Group group = groupRepository.findById(id).orElse(null);
        groupRepository.delete(group);
        groupRepository.flush();
        return true;
    }
    public Group updateGroup(Group group,Long id){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if(optionalGroup.isPresent()){
            Group updatedGroup = optionalGroup.get();
            updatedGroup.setName(group.getName());
            updatedGroup.setDescription(group.getDescription());
            groupRepository.saveAndFlush(updatedGroup);
            return updatedGroup;

        }
        return null;

    }
    public Group addGroup(Group group){
        return groupRepository.saveAndFlush(group);
    }

    public List<User> getMembers(Long id){
        return getGroupById(id).getUsers();
    }

}
