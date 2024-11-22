package com.losluminosos.medsystem.profiles.domain.model.aggregates;

import com.losluminosos.medsystem.shared.domain.model.entities.Specialty;
import com.losluminosos.medsystem.profiles.domain.model.valueobjects.EmailAddress;
import com.losluminosos.medsystem.profiles.domain.model.valueobjects.PersonName;
import com.losluminosos.medsystem.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Doctor extends AuditableAbstractAggregateRoot<Doctor> {

    @Column(unique = true, nullable = false) // UID debe ser único
    private String uid;

    private String licenseNumber;

    @Embedded
    private PersonName name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email_address"))})
    private EmailAddress email;

    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    public Doctor() {}

    public Doctor(String uid, String firstName, String lastName, String email, String phone, String licenceNumber, Specialty specialty) {
        this.uid = uid;
        this.name = new PersonName(firstName, lastName);
        this.email = new EmailAddress(email);
        this.phone = phone;
        this.licenseNumber = licenceNumber;
        this.specialty = specialty;
    }

    public String getFullName() {
        return name != null ? name.getFullName() : "Unknown Name";
    }

    public String getEmailAddress() {
        return email.address();
    }
}
