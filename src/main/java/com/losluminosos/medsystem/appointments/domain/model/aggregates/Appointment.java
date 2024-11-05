package com.losluminosos.medsystem.appointments.domain.model.aggregates;

import com.losluminosos.medsystem.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.losluminosos.medsystem.shared.domain.model.entities.Specialty;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class Appointment extends AuditableAbstractAggregateRoot<Appointment> {

    private Long doctorId;

    private Long patientId;

    private String date;

    private String reason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id")
    Specialty specialty;

    public Appointment() {}

    public Appointment(Long doctorId, Long patientId, String date, String reason, Specialty specialty){
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.reason = reason;
        this.specialty = specialty;
    }

    public Appointment updateReason(String reason) {
        this.reason = reason;
        return this;
    }
}
