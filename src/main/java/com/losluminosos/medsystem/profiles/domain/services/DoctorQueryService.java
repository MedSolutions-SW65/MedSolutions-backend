package com.losluminosos.medsystem.profiles.domain.services;

import com.losluminosos.medsystem.profiles.domain.model.aggregates.Doctor;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetAllDoctorsQuery;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetDoctorByIdQuery;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetDoctorByUidQuery;

import java.util.List;
import java.util.Optional;

public interface DoctorQueryService {
    Optional<Doctor> handle(GetDoctorByIdQuery query);
    Optional<Doctor> handle(GetDoctorByUidQuery query); // Nuevo método
    List<Doctor> handle(GetAllDoctorsQuery query);
}

