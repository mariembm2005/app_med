package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreneauService  {

    private final CreneauRepository repository;
    private final PlageHoraireRepository plageRepository;
    private final CreneauMapper mapper;

    public CreneauResponseDTO create(CreneauRequestDTO request) {

        Creneau creneau = mapper.toEntity(request);

        creneau.setPlageHoraire(
                plageRepository.findById(request.getPlageHoraireId())
                        .orElseThrow()
        );

        return mapper.toResponse(repository.save(creneau));
    }

    public List<CreneauResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public CreneauResponseDTO getById(Long id) {
        return mapper.toResponse(repository.findById(id).orElseThrow());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}