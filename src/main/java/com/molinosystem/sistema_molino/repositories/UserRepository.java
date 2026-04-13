package com.molinosystem.sistema_molino.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.molinosystem.sistema_molino.entities.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    List<User> findUserFilter(String userName, String name, boolean active, String rol);
    List<String>  findAllDistincRoles();
}
