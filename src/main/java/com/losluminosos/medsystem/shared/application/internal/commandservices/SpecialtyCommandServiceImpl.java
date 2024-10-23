package com.losluminosos.medsystem.shared.application.internal.commandservices;

import com.losluminosos.medsystem.shared.domain.model.commands.SeedSpecialtiesCommand;
import com.losluminosos.medsystem.shared.domain.model.entities.Specialty;
import com.losluminosos.medsystem.shared.domain.model.valueobjects.Specialties;
import com.losluminosos.medsystem.shared.domain.services.SpecialtyCommandService;
import com.losluminosos.medsystem.shared.infrastructure.persistence.jpa.repositories.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SpecialtyCommandServiceImpl implements SpecialtyCommandService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyCommandServiceImpl(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public void handle(SeedSpecialtiesCommand command) {
        Arrays.stream(Specialties.values()).forEach(specialty -> {
            if (!specialtyRepository.existsByName(specialty)) {
                specialtyRepository.save(new Specialty(specialty));
            }
        });
    }
}
