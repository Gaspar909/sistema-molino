package com.molinosystem.sistema_molino.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.molinosystem.sistema_molino.dtos.AuthResponse;
import com.molinosystem.sistema_molino.requests.AuthRquest;
import com.molinosystem.sistema_molino.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRquest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    
}
