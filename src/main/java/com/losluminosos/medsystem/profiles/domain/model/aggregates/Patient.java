package com.losluminosos.medsystem.profiles.domain.model.aggregates;

import com.losluminosos.medsystem.profiles.domain.model.commands.CreatePatientCommand;
import com.losluminosos.medsystem.profiles.domain.model.valueobjects.EmailAddress;
import com.losluminosos.medsystem.profiles.domain.model.valueobjects.PersonName;
import com.losluminosos.medsystem.profiles.domain.model.valueobjects.StreetAddress;
import com.losluminosos.medsystem.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

import lombok.Getter;


@Entity
@Getter
public class Patient extends AuditableAbstractAggregateRoot<Patient> {

    @Column(unique = true, nullable = false) // UID debe ser único
    private String uid;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street")),
            @AttributeOverride(name = "number", column = @Column(name = "address_number")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "address_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "address_country"))})
    private StreetAddress address;

    @Embedded
    private PersonName name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email_address"))})
    private EmailAddress email;

    private String phone;


    public Patient(){}


    public Patient(CreatePatientCommand command) {
        this.uid = command.uid();
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = new EmailAddress(command.email());
        this.phone = command.phone();
        this.address = new StreetAddress(command.street(), command.number(), command.city(), command.postalCode(), command.country());
    }

    public String getStreetAddress() {
        return address.getStreetAddress();
    }
    public String getFullName() {
        return name.getFullName();
    }

    public String getEmailAddress() {
        if (email == null || email.address() == null) {
            throw new RuntimeException("El paciente no tiene un correo electrónico válido.");
        }
        return email.address();
    }

}
