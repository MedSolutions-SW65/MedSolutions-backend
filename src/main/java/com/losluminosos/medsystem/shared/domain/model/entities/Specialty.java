package com.losluminosos.medsystem.shared.domain.model.entities;

import com.losluminosos.medsystem.shared.domain.model.valueobjects.Specialties;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Specialties name;

    public Specialty(){}

    public Specialty(Specialties name) {
        this();
        this.name = name;
    }

    public String getStringName() {
        return name.name();
    }

}
