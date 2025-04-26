package com.angerasilas.health_system_backend.service;

import java.util.List;

import com.angerasilas.health_system_backend.dto.LoginResponseDto;
import com.angerasilas.health_system_backend.dto.UserDto;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    UserDto getUserByPhone(String phone);
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    LoginResponseDto login(String email, String password);
}