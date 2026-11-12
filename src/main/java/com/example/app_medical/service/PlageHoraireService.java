package com.example.app_medical.service;

import com.example.app_medical.dto.*;
import com.example.app_medical.entity.*;
import com.example.app_medical.mapper.*;
import com.example.app_medical.repository.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlageHoraireService {

    private final PlageHoraireRepository repository;
    private final CreneauRepository creneauRepository;
    private final RendezVousRepository rendezVousRepository;
    private final MedecinRepository medecinRepository;
    private final PlageHoraireMapper mapper;

    public PlageHoraire create(PlageHoraireRequestDTO request) {

        PlageHoraire plage = mapper.toEntity(request);

        plage.setMedecin(
                medecinRepository.findById(request.getMedecinId())
                        .orElseThrow()
        );

        return repository.save(plage);
    }

    public List<PlageHoraire> getAll() {

        return repository.findAll()
                .stream()
                .toList();
    }
    public PlageHoraire update(PlageHoraireRequestDTO request, Long id) {
        Optional<PlageHoraire> plage = repository.findById(id);
        if (plage.isPresent()) {
            PlageHoraire po = plage.get();
            po.setId(id);
            po.setDate(request.getDate());
            po.setMedecin(medecinRepository.getById(request.getMedecinId()));
            po.setHeureDebut(request.getHeureDebut());
            po.setHeureFin(request.getHeureFin());
            repository.save(po);
            return po;
        }
        return null;
    }
    @Transactional
    public void delete(Long id) {

        PlageHoraire plage = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Plage horaire introuvable avec l'id : " + id
                ));

        List<Creneau> creneaux = new ArrayList<>(plage.getCreneaux());

        for (Creneau creneau : creneaux) {

            List<RendezVous> rendezVousList =
                    rendezVousRepository.findAll()
                            .stream()
                            .filter(rendezVous ->
                                    rendezVous.getCreneau() != null
                                            && rendezVous.getCreneau()
                                            .getId()
                                            .equals(creneau.getId())
                            )
                            .toList();

            for (RendezVous rendezVous : rendezVousList) {
                rendezVous.setCreneau(null);
                rendezVousRepository.save(rendezVous);
            }

            plage.getCreneaux().remove(creneau);
            creneauRepository.delete(creneau);
        }

        repository.delete(plage);
    }

    public PlageHoraire findById(Long id) {
        Optional<PlageHoraire> po = repository.findById(id);
        if (po.isPresent()) {
            return po.get();
        }
        return null;
    }
}