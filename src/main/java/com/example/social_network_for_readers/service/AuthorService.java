package com.example.social_network_for_readers.service;


import com.example.social_network_for_readers.entity.Author;
import com.example.social_network_for_readers.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private AuthorRepository authorRepository;
    public Author getAuthorById(@PathVariable Long id) {
        return this.authorRepository.findById(id).get();
    }
    public List<Author> getAllAuthors() {
        return this.authorRepository.findAll();
    }
    public Author updateAuthor(Long id,Author a) {
        Optional<Author> oauthor = this.authorRepository.findById(id);
        if(oauthor.isPresent()){
            Author author = oauthor.get();
            author.setName(a.getName());
            return authorRepository.saveAndFlush(author);


        }
        return null;

    }
    public void deleteAuthor(Long id) {
        Optional<Author> author = this.authorRepository.findById(id);
        if(author.isPresent()){
            Author author1 = author.get();
            this.authorRepository.delete(author1);
            this.authorRepository.flush();
            System.out.println("Author"+author1.getName()+" has been deleted");
        }
    }
    public Author addAuthor(Author author) {
        return authorRepository.saveAndFlush(author);
    }
}
