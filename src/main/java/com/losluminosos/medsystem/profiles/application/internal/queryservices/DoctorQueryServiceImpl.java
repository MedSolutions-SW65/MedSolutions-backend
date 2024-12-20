package com.losluminosos.medsystem.profiles.application.internal.queryservices;

import com.losluminosos.medsystem.profiles.domain.model.aggregates.Doctor;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetAllDoctorsQuery;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetDoctorByIdQuery;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetDoctorByUidQuery;
import com.losluminosos.medsystem.profiles.domain.services.DoctorQueryService;
import com.losluminosos.medsystem.profiles.infrastructure.persistence.jpa.repositories.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorQueryServiceImpl implements DoctorQueryService {
    private final DoctorRepository doctorRepository;

    public DoctorQueryServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Optional<Doctor> handle(GetDoctorByIdQuery query) {
        return doctorRepository.findById(query.id());
    }

    @Override
    public List<Doctor> handle(GetAllDoctorsQuery query) {
        return doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> handle(GetDoctorByUidQuery query) {
        return doctorRepository.findByUid(query.uid());
    }
}
