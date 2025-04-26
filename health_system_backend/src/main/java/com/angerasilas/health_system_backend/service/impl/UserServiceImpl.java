package com.angerasilas.health_system_backend.service.impl;

import com.angerasilas.health_system_backend.dto.LoginResponseDto;
import com.angerasilas.health_system_backend.dto.UserDto;
import com.angerasilas.health_system_backend.entity.DoctorDetails;
import com.angerasilas.health_system_backend.entity.UserEntity;
import com.angerasilas.health_system_backend.mapper.UserMapper;
import com.angerasilas.health_system_backend.repository.DoctorDetailsRepository;
import com.angerasilas.health_system_backend.repository.UserRepository;
import com.angerasilas.health_system_backend.security.JwtUtil;
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
    private final DoctorDetailsRepository doctorDetailsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
        return UserMapper.toDto(userEntity);
    }

    @Override
    public UserDto getUserByPhone(String phone) {
        UserEntity userEntity = userRepository.findByPhone(phone);
        if (userEntity == null) {
            throw new RuntimeException("User not found");
        }
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

    @Override
    public LoginResponseDto login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        // Fetch doctorId if the user is a doctor
        Long doctorId = null;
        if ("DOCTOR".equalsIgnoreCase(user.getRole())) {
            DoctorDetails doctorDetails = doctorDetailsRepository.findByUserId(user.getId());
            if (doctorDetails != null) {
                doctorId = doctorDetails.getId();
            }
        }

        return new LoginResponseDto(user.getId(), doctorId, user.getRole(), token);
    }
}