package com.losluminosos.medsystem.profiles.domain.model.aggregates;

import com.losluminosos.medsystem.profiles.domain.model.commands.CreateDoctorCommand;
import com.losluminosos.medsystem.profiles.domain.model.valueobjects.EmailAddress;
import com.losluminosos.medsystem.profiles.domain.model.valueobjects.PersonName;
import com.losluminosos.medsystem.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Doctor extends AuditableAbstractAggregateRoot<Doctor> {

    private String specialty;

    private String licenseNumber;

    @Embedded
    private PersonName name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email_address"))})
    private EmailAddress email;

    private String phone;

    public Doctor(){}

    public Doctor(CreateDoctorCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.phone = command.phone();
        this.specialty = command.specialty();
        this.licenseNumber = command.licenceNumber();
    }

    public String getFullName() {
        return name.getFullName();
    }

    public String getEmailAddress() {
        return email.address();
    }}
