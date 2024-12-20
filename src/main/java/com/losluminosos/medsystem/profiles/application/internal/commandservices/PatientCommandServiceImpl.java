package com.losluminosos.medsystem.profiles.application.internal.commandservices;

import com.losluminosos.medsystem.profiles.domain.model.aggregates.Patient;
import com.losluminosos.medsystem.profiles.domain.model.commands.CreatePatientCommand;
import com.losluminosos.medsystem.profiles.domain.services.PatientCommandService;
import com.losluminosos.medsystem.profiles.infrastructure.persistence.jpa.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientCommandServiceImpl implements PatientCommandService {
    private final PatientRepository patientRepository;

    public PatientCommandServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Optional<Patient> handle(CreatePatientCommand command) {


        // Validar si el UID ya existe
        if (patientRepository.existsByUid(command.uid())) {
            throw new IllegalArgumentException("A patient with this UID already exists.");
        }
        var patient = new Patient(command);
        patientRepository.save(patient);
        return Optional.of(patient);
    }


}
