package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlageHoraireService {

    private final PlageHoraireRepository repository;
    private final MedecinRepository medecinRepository;
    private final PlageHoraireMapper mapper;

    public PlageHoraireResponseDTO create(PlageHoraireRequestDTO request) {

        PlageHoraire plage = mapper.toEntity(request);

        plage.setMedecin(
                medecinRepository.findById(request.getMedecinId())
                        .orElseThrow()
        );

        return mapper.toResponse(repository.save(plage));
    }

    public List<PlageHoraireResponseDTO> getAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    public PlageHoraireResponseDTO update(PlageHoraireRequestDTO request, Long id) {
        Optional<PlageHoraire> plage = repository.findById(id);
        if (plage.isPresent()) {
            PlageHoraire po = plage.get();
            po.setId(id);
            po.setDate(request.getDate());
            po.setMedecin(medecinRepository.getById(request.getMedecinId()));
            po.setHeureDebut(request.getHeureDebut());
            po.setHeureFin(request.getHeureFin());
            repository.save(po);
            return mapper.toResponse(po);
        }
        return null;
    }
    public boolean delete(long id) {
        Optional<PlageHoraire> po = repository.findById(id);
        if (po.isPresent()) {
            repository.delete(po.get());
            return true;
        }
        return false;
    }

    public PlageHoraireResponseDTO findById(Long id) {
        Optional<PlageHoraire> po = repository.findById(id);
        if (po.isPresent()) {
            return mapper.toResponse(po.get());
        }
        return null;
    }
}