package com.example.social_network_for_readers.Controller;


import com.example.social_network_for_readers.dto.WorkRequestDTO;
import com.example.social_network_for_readers.dto.WorkResponseDTO;
import com.example.social_network_for_readers.entity.Author;
import com.example.social_network_for_readers.entity.User;
import com.example.social_network_for_readers.entity.Work;
import com.example.social_network_for_readers.mapper.GroupMapper;
import com.example.social_network_for_readers.mapper.WorkMapper;
import com.example.social_network_for_readers.service.AuthorService;
import com.example.social_network_for_readers.service.WorkService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/works")
@AllArgsConstructor
public class WorkController {
    private WorkService workService;
    private AuthorService authorService;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private WorkMapper workMapper;

    @GetMapping
    public List<WorkResponseDTO> getWorks() {
        return workMapper.toDTO(this.workService.getAllWork());
    }
    @PostMapping
    public WorkResponseDTO createWork(@RequestBody WorkRequestDTO work) {
        return workMapper.toDTO(this.workService.createWork(workMapper.toEntity(work)));
    }
    @PutMapping("/{id}")
    public WorkResponseDTO updateWork(@RequestBody WorkRequestDTO work, @PathVariable Long id) {
        return workMapper.toDTO(this.workService.updateWork(id,workMapper.toEntity(work)));

    }
    @GetMapping("/{id}")
    public WorkResponseDTO getWork(@PathVariable Long id) {
        return workMapper.toDTO(this.workService.getWorkById(id));
    }
    @DeleteMapping("/{id}")
    public String deleteWork(@PathVariable Long id) {
        return this.workService.deleteWork(id);
    }

    @PostMapping("/{id}/{AId}")
    public ResponseEntity<WorkResponseDTO> addAuthor(@PathVariable Long AId,@PathVariable Long id) {
        Work w = this.workService.getWorkById(id);
        Author a = this.authorService.getAuthorById(AId);
        this.workService.addAuthor(a,w);
        return ResponseEntity.status(201).body(workMapper.toDTO(w));

    }

    @DeleteMapping("/{id}/{AuthorId}")
    public ResponseEntity<WorkResponseDTO> deleteAuthor(@PathVariable Long id, @PathVariable Long AuthorId) {
        Author a =  this.authorService.getAuthorById(AuthorId);
        Work w = this.workService.getWorkById(id);
        this.workService.deleteAuthor(a,w);
        return ResponseEntity.status(201).body(workMapper.toDTO(w));

    }

}
