package com.losluminosos.medsystem.auth.interfaces.rest.transform;

import com.losluminosos.medsystem.auth.domain.model.aggregates.User;
import com.losluminosos.medsystem.auth.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(user.getId(), user.getUsername(), user.getRole().getStringName());
    }
}
