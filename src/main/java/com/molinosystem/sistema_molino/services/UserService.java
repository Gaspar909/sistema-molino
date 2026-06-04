package com.molinosystem.sistema_molino.services;

import jakarta.persistence.criteria.Expression;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.molinosystem.sistema_molino.dtos.UserDto;
import com.molinosystem.sistema_molino.entities.User;
import com.molinosystem.sistema_molino.exceptions.NoFoundException;
import com.molinosystem.sistema_molino.exceptions.BadRequestException;
import com.molinosystem.sistema_molino.mappers.Mapper;
import com.molinosystem.sistema_molino.repositories.UserRepository;
import com.molinosystem.sistema_molino.requests.ChangePasswordRequest;
import com.molinosystem.sistema_molino.requests.UpdateUserRequest;
import com.molinosystem.sistema_molino.requests.UserCreateRequest;

@Service
public class UserService implements IUserService {

    @Autowired 
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserCreateRequest newUserRquest) {
        if (userRepository.existsByUserName(newUserRquest.getUserName()))
            throw new BadRequestException("This username is already in use");

        String passEncripted = passwordEncoder.encode(newUserRquest.getPassword());
        User newUser = new User(null, 
            newUserRquest.getName(), 
            newUserRquest.getUserName(), 
            passEncripted, 
            newUserRquest.getRol(), 
            true);

            return  Mapper.toDTO(userRepository.save(newUser));
    }

    @Override
    public Page<UserDto> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> result = userRepository.findAll(pageable);
        return result.map(Mapper::toDTO);
    }

    @Override
    public UserDto getUserById(Long id) {
        User result = userRepository.getReferenceById(id);
        return Mapper.toDTO(result);
    }

    @Override
    public Page<UserDto> searchUsers(int page, int size, String search, String rol, Boolean active) {
        Specification<User> specification = Specification.where((root, query, cb) -> cb.conjunction());

        if(search != null && !search.isEmpty()) {
            specification = specification.and((root, query, cb) -> { 
                Expression<String> concatExpression = cb.concat(
                    cb.concat(root.get("userName"), " "), root.get("name")
                );
                return cb.like(cb.lower(concatExpression), "%" + search.toLowerCase() + "%"); 
        });
        }

        if(rol !=null && !rol.isEmpty())
            specification = specification.and((root, query, cb) -> cb.like(root.get("rol"), "%" + rol + "%"));

        if(active != null)
            specification = specification.and((root, query, cb) -> cb.equal(root.get("active"), active));

        Pageable pageable = PageRequest.of(page, size);
        Page<User> result = userRepository.findAll(specification, pageable);
        return result.map(Mapper::toDTO);
    }

    @Override
    public List<String> getRoles() {
        return userRepository.findAllDistincRoles();
    }

    @Override
    public UserDto updateUser(UpdateUserRequest upUser) {
        User userTmp = userRepository.findById(upUser.getId())
        .orElseThrow(() -> new NoFoundException("User not exist"));
        
        userTmp.setName(upUser.getName());
        userTmp.setUserName(upUser.getUserName());
        userTmp.setRol(upUser.getRol());

        return Mapper.toDTO(userRepository.save(userTmp));
    }

    @Override
    public void changePassword(ChangePasswordRequest request){
        User userTmp = userRepository.findById(request.getId()) 
        .orElseThrow(() -> new NoFoundException("User does not exist"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), userTmp.getPassword())){
            throw new BadRequestException("Current password does not match");
        }

        userTmp.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(userTmp);
    }

    @Override
    public void resetPassword(Long id, String newPassword){
        User userTmp = userRepository.findById(id) 
        .orElseThrow(() -> new NoFoundException("User does not exist"));

        userTmp.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(userTmp);
    }

    @Override
    public UserDto enableUser(Long id){
        User userTmp = userRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("User does not exist"));

        userTmp.setActive(true);

        return Mapper.toDTO(userRepository.save(userTmp));
    }

    @Override
    public UserDto disableUser(Long id) {
        User userTmp = userRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("User not exist"));

        userTmp.setActive(false);

        return Mapper.toDTO(userRepository.save(userTmp));
    }

    @Override
    public UserDto deleteUser(Long id) {
        User userTmp = userRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("User not exist"));

        userRepository.delete(userTmp);

        return Mapper.toDTO(userTmp);
    }

}
