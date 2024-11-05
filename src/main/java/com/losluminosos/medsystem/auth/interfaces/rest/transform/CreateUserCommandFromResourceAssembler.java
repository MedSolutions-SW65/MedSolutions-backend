package com.losluminosos.medsystem.auth.interfaces.rest.transform;

import com.losluminosos.medsystem.auth.domain.model.commands.CreateUserCommand;
import com.losluminosos.medsystem.auth.interfaces.rest.resources.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {
    public static CreateUserCommand toCommandFromResource(CreateUserResource resource) {
        return new CreateUserCommand(resource.username(), resource.password(), resource.role());
    }
}
