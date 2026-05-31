package com.molinosystem.sistema_molino.services;

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
    private UserRepository _userRepository;

    @Autowired
    private BCryptPasswordEncoder _passwordEncoder;

    @Override
    public UserDto createUser(UserCreateRequest newUserRquest) {
        if (_userRepository.existsByUserName(newUserRquest.getUserName()))
            throw new BadRequestException("This username is already in use");

        String passEncripted = _passwordEncoder.encode(newUserRquest.getPassword());
        User newUser = new User(null, 
            newUserRquest.getName(), 
            newUserRquest.getUserName(), 
            passEncripted, 
            newUserRquest.getRol(), 
            true);

            return  Mapper.toDTO(_userRepository.save(newUser));
    }

    @Override
    public Page<UserDto> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> result = _userRepository.findAll(pageable);
        return result.map(Mapper::toDTO);
    }

    @Override
    public UserDto getUserById(Long id) {
        User result = _userRepository.getReferenceById(id);
        return Mapper.toDTO(result);
    }

    @Override
    public Page<UserDto> searchUsers(int page, int size, String userName, String name, String rol, Boolean active) {
        Specification<User> specification = Specification.where((root, query, cb) -> cb.conjunction());

        if(userName != null && !userName.isEmpty()) 
            specification = specification.and((root, query, cb) -> cb.like(root.get("userName"), "%" + userName + "%"));

        if(name !=null && !name.isEmpty())
            specification = specification.and((root, query, cb) -> cb.like(root.get("name"), "%" + userName + "%"));

        if(rol !=null && !rol.isEmpty())
            specification = specification.and((root, query, cb) -> cb.like(root.get("rol"), "%" + rol + "%"));

        if(active != null)
            specification = specification.and((root, query, cb) -> cb.equal(root.get("active"), active));

        Pageable pageable = PageRequest.of(page, size);
        Page<User> result = _userRepository.findAll(specification, pageable);
        return result.map(Mapper::toDTO);
    }

    @Override
    public List<String> getRoles() {
        return _userRepository.findAllDistincRoles();
    }

    @Override
    public UserDto updateUser(UpdateUserRequest upUser) {
        User userTmp = _userRepository.findById(upUser.getId())
        .orElseThrow(() -> new NoFoundException("User not exist"));
        
        userTmp.setName(upUser.getName());
        userTmp.setUserName(upUser.getUserName());
        userTmp.setRol(upUser.getRol());

        return Mapper.toDTO(_userRepository.save(userTmp));
    }

    @Override
    public void changePassword(ChangePasswordRequest request){
        User userTmp = _userRepository.findById(request.getId()) 
        .orElseThrow(() -> new NoFoundException("User does not exist"));

        if (!_passwordEncoder.matches(request.getCurrentPassword(), userTmp.getPassword())){
            throw new BadRequestException("Current password does not match");
        }

        userTmp.setPassword(_passwordEncoder.encode(request.getNewPassword()));

        _userRepository.save(userTmp);
    }

    @Override
    public void resetPassword(Long id, String newPassword){
        User userTmp = _userRepository.findById(id) 
        .orElseThrow(() -> new NoFoundException("User does not exist"));

        userTmp.setPassword(_passwordEncoder.encode(newPassword));

        _userRepository.save(userTmp);
    }

    @Override
    public UserDto enableUser(Long id){
        User userTmp = _userRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("User does not exist"));

        userTmp.setActive(true);

        return Mapper.toDTO(_userRepository.save(userTmp));
    }

    @Override
    public UserDto disableUser(Long id) {
        User userTmp = _userRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("User not exist"));

        userTmp.setActive(false);

        return Mapper.toDTO(_userRepository.save(userTmp));
    }

    @Override
    public UserDto deleteUser(Long id) {
        User userTmp = _userRepository.findById(id)
        .orElseThrow(() -> new NoFoundException("User not exist"));

        _userRepository.delete(userTmp);

        return Mapper.toDTO(userTmp);
    }

}
