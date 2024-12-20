package com.losluminosos.medsystem.profiles.domain.services;

import com.losluminosos.medsystem.profiles.domain.model.aggregates.Patient;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetAllPatientsQuery;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetPatientByIdQuery;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetPatientByUidQuery;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetPatientByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface PatientQueryService {
    Optional<Patient> handle(GetPatientByIdQuery query);
    List<Patient> handle(GetAllPatientsQuery query);
    Optional<Patient> handle(GetPatientByUidQuery query);
}
