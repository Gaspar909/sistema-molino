package com.molinosystem.sistema_molino.services;

import com.molinosystem.sistema_molino.dtos.AuthResponse;
import com.molinosystem.sistema_molino.requests.AuthRquest;

public interface IAuthService {
    public AuthResponse login(AuthRquest request);
}
