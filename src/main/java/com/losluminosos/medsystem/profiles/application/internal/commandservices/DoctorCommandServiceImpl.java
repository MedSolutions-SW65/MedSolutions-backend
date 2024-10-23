package com.losluminosos.medsystem.profiles.application.internal.commandservices;

import com.losluminosos.medsystem.profiles.domain.model.aggregates.Doctor;
import com.losluminosos.medsystem.profiles.domain.model.commands.CreateDoctorCommand;
import com.losluminosos.medsystem.shared.domain.model.valueobjects.Specialties;
import com.losluminosos.medsystem.profiles.domain.services.DoctorCommandService;
import com.losluminosos.medsystem.profiles.infrastructure.persistence.jpa.repositories.DoctorRepository;
import com.losluminosos.medsystem.shared.infrastructure.persistence.jpa.repositories.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorCommandServiceImpl implements DoctorCommandService {
    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;

    public DoctorCommandServiceImpl(DoctorRepository doctorRepository, SpecialtyRepository specialtyRepository) {
        this.doctorRepository = doctorRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Optional<Doctor> handle(CreateDoctorCommand command) {
        var specialty = Specialties.valueOf(command.specialty());
        var doctor = new Doctor(command.firstName(), command.lastName(), command.email(), command.phone(), command.licenceNumber(), specialtyRepository.findByName(specialty).get());
        doctorRepository.save(doctor);
        return Optional.of(doctor);
    }
}
