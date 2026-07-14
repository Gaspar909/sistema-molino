package com.molinosystem.sistema_molino.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.molinosystem.sistema_molino.dtos.UserDto;
import com.molinosystem.sistema_molino.requests.ChangePasswordRequest;
import com.molinosystem.sistema_molino.requests.UpdateUserRequest;
import com.molinosystem.sistema_molino.requests.UserCreateRequest;
import com.molinosystem.sistema_molino.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping ("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers( 
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(userService.getUsers(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<UserDto>> getSearch(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size, 
        @RequestParam(required = false) String search, 
        @RequestParam(required = false) String rol, 
        @RequestParam(required = false) Boolean active
    ) {
        return ResponseEntity.ok(userService.searchUsers(page, size, search, rol, active));
    }
    
    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles() {
        return ResponseEntity.ok(userService.getRoles());
    }
    

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateRequest newUser){
        return ResponseEntity.status(201).body(userService.createUser(newUser));
    }
    
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserRequest update) {
        return ResponseEntity.ok(userService.updateUser(update));
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reset/{id}")
    public ResponseEntity<Void> resetPassword(@PathVariable Long id, @RequestParam String newPassword){
        userService.resetPassword(id, newPassword);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<UserDto> enableUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.enableUser(id));
    }

    @DeleteMapping("/disable/{id}")
    public ResponseEntity<UserDto> disableUser (@PathVariable Long id){
        return ResponseEntity.ok(userService.disableUser(id));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
