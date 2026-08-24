package com.example.social_network_for_readers.Controller;


import com.example.social_network_for_readers.entity.Author;
import com.example.social_network_for_readers.entity.User;
import com.example.social_network_for_readers.entity.Work;
import com.example.social_network_for_readers.service.AuthorService;
import com.example.social_network_for_readers.service.WorkService;
import lombok.AllArgsConstructor;
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
    @GetMapping
    public List<Work> getWorks() {
        return this.workService.getAllWork();
    }
    @PostMapping
    public Work createWork(@RequestBody Work work) {
        return this.workService.createWork(work);
    }
    @PutMapping("/{id}")
    public Work updateWork(@RequestBody Work work, @PathVariable Long id) {
        return this.workService.updateWork(id,work);

    }
    @GetMapping("/{id}")
    public Work getWork(@PathVariable Long id) {
        return this.workService.getWorkById(id);
    }
    @DeleteMapping("/{id}")
    public String deleteWork(@PathVariable Long id) {
        return this.workService.deleteWork(id);
    }

    @PostMapping("/{id}/{AId}")
    public ResponseEntity<Work> addAuthor(@PathVariable Long AId,@PathVariable Long id) {
        Work w = this.workService.getWorkById(id);
        Author a = this.authorService.getAuthorById(AId);
        this.workService.addAuthor(a,w);
        return ResponseEntity.status(201).body(w);

    }

    @DeleteMapping("/{id}/{AuthorId}")
    public ResponseEntity<Work> deleteAuthor(@PathVariable Long id, @PathVariable Long AuthorId) {
        Author a =  this.authorService.getAuthorById(AuthorId);
        Work w = this.workService.getWorkById(id);
        this.workService.deleteAuthor(a,w);
        return ResponseEntity.status(201).body(w);

    }

}
