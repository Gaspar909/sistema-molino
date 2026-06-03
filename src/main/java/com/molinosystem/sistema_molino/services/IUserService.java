package com.molinosystem.sistema_molino.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.molinosystem.sistema_molino.dtos.UserDto;
import com.molinosystem.sistema_molino.requests.ChangePasswordRequest;
import com.molinosystem.sistema_molino.requests.UpdateUserRequest;
import com.molinosystem.sistema_molino.requests.UserCreateRequest;

public interface IUserService {
    public UserDto createUser(UserCreateRequest newUserRquest);
    public Page<UserDto> getUsers(int page, int size);
    public UserDto getUserById(Long id);
    public Page<UserDto> searchUsers(int page, int size, String userName, String name, String rol, Boolean active);
    public List<String> getRoles();
    public UserDto updateUser(UpdateUserRequest upUser);
    public void changePassword(ChangePasswordRequest reuquest);
    public void resetPassword (Long id, String newPassword);
    public UserDto enableUser(Long id);
    UserDto disableUser(Long id);
    UserDto deleteUser (Long id);
}
