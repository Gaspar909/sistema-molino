package com.molinosystem.sistema_molino.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.molinosystem.sistema_molino.dtos.AuthResponse;
import com.molinosystem.sistema_molino.entities.User;
import com.molinosystem.sistema_molino.exceptions.BadLoginExeption;
import com.molinosystem.sistema_molino.repositories.UserRepository;
import com.molinosystem.sistema_molino.requests.AuthRquest;

@Service
public class AuthService implements IAuthService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthResponse login(AuthRquest request){
        User user = userRepository.findByUserName(request.getUserName())
        .orElseThrow( () -> new BadLoginExeption("User not exist"));

        if(!user.isActive()) throw new BadLoginExeption("User is disable");

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BadLoginExeption("Incorrect password");

        String token = jwtService.generateToken(user.getUserName(), user.getRol());

        return AuthResponse.builder()
            .token(token)
            .id(user.getId())
            .userName(user.getUserName())
            .name(user.getName())
            .rol(user.getRol())
            .build();
    }
}
