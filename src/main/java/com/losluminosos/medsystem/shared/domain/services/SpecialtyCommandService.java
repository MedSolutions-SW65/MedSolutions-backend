package com.losluminosos.medsystem.shared.domain.services;

import com.losluminosos.medsystem.shared.domain.model.commands.SeedSpecialtiesCommand;

public interface SpecialtyCommandService {
    void handle(SeedSpecialtiesCommand command);
}
