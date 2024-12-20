package com.losluminosos.medsystem.profiles.interfaces.rest.transform;

import com.losluminosos.medsystem.profiles.domain.model.commands.CreateDoctorCommand;
import com.losluminosos.medsystem.profiles.interfaces.rest.resources.CreateDoctorResource;

public class CreateDoctorCommandFromResourceAssembler {
    public static CreateDoctorCommand toCommandFromResource(CreateDoctorResource resource) {
        return new CreateDoctorCommand(
                resource.uid(), // UID
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.phone(),
                resource.licenceNumber(),
                resource.specialty()
        );
    }
}
