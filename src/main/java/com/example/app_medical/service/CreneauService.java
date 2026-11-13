package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreneauService  {

    private final CreneauRepository repository;
    private final PlageHoraireRepository plageRepository;
    private final RendezVousRepository rendezVousRepository;
    private final MedecinRepository medecinRepository;
    private final CreneauMapper mapper;

    public CreneauResponseDTO create(CreneauRequestDTO request) {

        Creneau creneau = mapper.toEntity(request);

        creneau.setPlageHoraire(
                plageRepository.findById(request.getPlageHoraireId())
                        .orElseThrow()
        );

        return mapper.toDTO(repository.save(creneau));
    }

    public List<CreneauResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public CreneauResponseDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    public boolean delete(Long id) {
        Creneau creneau = repository.findById(id).orElseThrow();
        if(creneau!=null){
            repository.deleteById(id);
            return true;
        }
        return false;

    }
    public CreneauResponseDTO update(CreneauRequestDTO request,long id) {
        Optional<Creneau> creneau = repository.findById(id);
        if(creneau.isPresent()) {
            Creneau creneauUpdate = mapper.toEntity(request);
            creneauUpdate.setPlageHoraire(plageRepository.getReferenceById(request.getPlageHoraireId()));
            creneauUpdate.setId(id);
            creneauUpdate.setHeureDebut(request.getHeureDebut());
            creneauUpdate.setHeureFin(request.getHeureFin());
            creneauUpdate.setTypeSoin(request.getTypeSoin());
            repository.save(creneauUpdate);
            return  mapper.toDTO(creneauUpdate);
        }
        return null;
    }
    public List<CreneauResponseDTO> findDisponibles(){
        return repository.findByDisponibleTrue().stream().map(dispo->mapper.toDTO(dispo)).toList();
    }
    public CreneauResponseDTO reserver(Long id) {
        Optional<Creneau> creneau = repository.findById(id);
        if(creneau.isPresent()) {
            Creneau creneauUpdate = creneau.get();
            creneauUpdate.setDisponible(false);
            repository.save(creneauUpdate);
            return  mapper.toDTO(creneauUpdate);
        }
        return null;

    }

    public CreneauResponseDTO liberer(Long id) {
        Optional<Creneau> creneau = repository.findById(id);
        if(creneau.isPresent()) {
            Creneau creneauUpdate = creneau.get();
            creneauUpdate.setDisponible(true);
            repository.save(creneauUpdate);
            return  mapper.toDTO(creneauUpdate);
        }
        return null;

    }
    public void changerDisponibilite(Long id, boolean disponible) {

        Creneau creneau = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Créneau introuvable"));

        creneau.setDisponible(disponible);

        repository.save(creneau);
    }
}