package com.angerasilas.health_system_backend.mapper;

import com.angerasilas.health_system_backend.dto.UserDto;
import com.angerasilas.health_system_backend.entity.UserEntity;

public class UserMapper {

    public static UserDto toDto(UserEntity userEntity) {
        return new UserDto(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getPhone(),
                null, // Password is not included in DTO
                userEntity.getRole()
        );
    }

    public static UserEntity toEntity(UserDto userDto) {
        return new UserEntity(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPhone(),
                userDto.getPassword(), // Password is not included in DTO
                userDto.getRole(),
                null,
                null
        );
    }
}