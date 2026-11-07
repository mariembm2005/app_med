package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.util.List;

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
}