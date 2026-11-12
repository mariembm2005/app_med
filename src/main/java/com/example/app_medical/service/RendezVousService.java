package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;
    private final CreneauRepository creneauRepository;
    private final RendezVousMapper mapper;

    public RendezVousResponseDTO reserver(
            RendezVousRequestDTO request) {

        Patient patient =
                patientRepository.findById(request.getPatientId())
                        .orElseThrow();

        Creneau creneau =
                creneauRepository.findById(request.getCreneauId())
                        .orElseThrow();

        if (!creneau.getDisponible()) {
            throw new RuntimeException(
                    "Ce créneau est déjà réservé");
        }

        RendezVous rdv = new RendezVous();

        rdv.setDateReservation(LocalDate.now());
        rdv.setConfirme(false);
        rdv.setPatient(patient);
        rdv.setCreneau(creneau);

        creneau.setDisponible(false);

        creneauRepository.save(creneau);

        return mapper.toDTO(rendezVousRepository.save(rdv));
    }


    public RendezVousResponseDTO confirmer(Long id) {

        RendezVous rdv =
                rendezVousRepository.findById(id).orElseThrow();

        rdv.setConfirme(true);

        return mapper.toDTO(rendezVousRepository.save(rdv));
    }

    public RendezVousResponseDTO annuler(Long id) {

        RendezVous rdv =
                rendezVousRepository.findById(id).orElseThrow();
        if(rdv!=null) {
            rdv.getCreneau().setDisponible(true);

            rendezVousRepository.delete(rdv);
            return mapper.toDTO(rdv);
        }
        return null;


    }
    public RendezVousResponseDTO create(
            RendezVousRequestDTO dto) {

        Patient patient =
                patientRepository.findById(dto.getPatientId())
                        .orElseThrow(() ->
                                new RuntimeException("Patient not found"));

        Creneau creneau =
                creneauRepository.findById(dto.getCreneauId())
                        .orElseThrow(() ->
                                new RuntimeException("Creneau not found"));

        RendezVous rendezVous =
                mapper.toEntity(dto);

        rendezVous.setDateReservation(LocalDate.now());
        rendezVous.setConfirme(false);

        RendezVous saved =
                rendezVousRepository.save(rendezVous);

        return mapper.toDTO(saved);
    }

    public List<RendezVousResponseDTO> getAll() {

        return rendezVousRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public RendezVousResponseDTO getById(Long id) {

        RendezVous rendezVous =
                rendezVousRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Rendez-vous not found"));

        return mapper.toDTO(rendezVous);
    }

    public RendezVousResponseDTO update(
            Long id,
            RendezVousRequestDTO dto) {

        RendezVous rendezVous =
                rendezVousRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Rendez-vous not found"));

        Patient patient =
                patientRepository.findById(dto.getPatientId())
                        .orElseThrow(() ->
                                new RuntimeException("Patient not found"));

        Creneau creneau =
                creneauRepository.findById(dto.getCreneauId())
                        .orElseThrow(() ->
                                new RuntimeException("Creneau not found"));

        rendezVous.setPatient(patient);
        rendezVous.setCreneau(creneau);

        return mapper.toDTO(
                rendezVousRepository.save(rendezVous)
        );
    }

    public boolean delete(Long id) {
        Optional<RendezVous> rd = rendezVousRepository.findById(id);
        if (rd.isPresent()) {
            RendezVous rdv = rd.get();
            rendezVousRepository.delete(rdv);
            return true;

        }
        return false;
    }
}