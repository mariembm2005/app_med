package com.example.social_network_for_readers.service;

import com.example.social_network_for_readers.entity.Author;
import com.example.social_network_for_readers.entity.Work;
import com.example.social_network_for_readers.repository.AuthorRepository;
import com.example.social_network_for_readers.repository.WorkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkService {
    private WorkRepository workRepository;
    public void addAuthor(Long workId, Author author) {

        Work work = workRepository.findById(workId)
                .orElseThrow();

        work.getAuthors().add(author);

        workRepository.saveAndFlush(work);
    }
    public void deleteAuthor(Long workId, Author author) {

        Work work = workRepository.findById(workId)
                .orElseThrow();

        work.getAuthors().remove(author);

        workRepository.saveAndFlush(work);
    }
    public List<Work> getAllWork(){
        return this.workRepository.findAll();
    }
    public Work getWorkById(Long workId){
        return this.workRepository.findById(workId)
                .orElse(null);
    }
    public Work updateWork(Long workId, Work wr) {

        Optional<Work> work = workRepository.findById(workId);

        if (work.isPresent()) {

            Work w = work.get();

            w.setImage(wr.getImage());
            w.setTitle(wr.getTitle());
            w.setOriginalPubYear(wr.getOriginalPubYear());

            return workRepository.saveAndFlush(w);
        }

        return null;
    }
    public String deleteWork(Long workId){
        Optional<Work> work = this.workRepository.findById(workId);
        if(work.isPresent()){
            this.workRepository.delete(work.get());
            this.workRepository.flush();
            return "Work has been deleted";
        }
        return null;
    }

    public Work createWork(Work work){
        return this.workRepository.saveAndFlush(work);
    }

    public Work addAuthor(Author a,Work work){
        Work w = workRepository.findById(work.getId())
                .orElseThrow();
        w.getAuthors().add(a);
        return workRepository.saveAndFlush(w);
    }

    public Work deleteAuthor(Author author,Work work){
        Work w = workRepository.findById(work.getId())
                .orElseThrow();
        work.getAuthors().remove(author);
        return workRepository.saveAndFlush(work);
    }


}
