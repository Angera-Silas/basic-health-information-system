package com.angerasilas.health_system_backend.service.impl;

import com.angerasilas.health_system_backend.dto.UserDto;
import com.angerasilas.health_system_backend.entity.UserEntity;
import com.angerasilas.health_system_backend.mapper.UserMapper;
import com.angerasilas.health_system_backend.repository.UserRepository;
import com.angerasilas.health_system_backend.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDto(userEntity);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = UserMapper.toEntity(userDto);

        // Hash the password before saving
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);
        return UserMapper.toDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPhone(userDto.getPhone());
        userEntity.setRole(userDto.getRole());
        UserEntity updatedUser = userRepository.save(userEntity);
        return UserMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}