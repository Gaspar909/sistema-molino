package com.molinosystem.sistema_molino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.molinosystem.sistema_molino.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
