package com.uca.parcialfinalncapas.utils.mappers;

import com.uca.parcialfinalncapas.dto.request.UserCreateRequest;
import com.uca.parcialfinalncapas.dto.request.UserUpdateRequest;
import com.uca.parcialfinalncapas.dto.response.UserResponse;
import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.utils.enums.Rol;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntityCreate(UserCreateRequest userRequest) {
        return User.builder()
                .nombre(userRequest.getNombre())
                .correo(userRequest.getCorreo())
                .password(userRequest.getPassword())
                .nombreRol(Rol.valueOf(userRequest.getNombreRol().toUpperCase()))
                .build();
    }

    public static User toEntityUpdate(UserUpdateRequest userUpdate) {
        return User.builder()
                .id(userUpdate.getId())
                .nombre(userUpdate.getNombre())
                .password(userUpdate.getPassword())
                .nombreRol(Rol.valueOf(userUpdate.getNombreRol().toUpperCase()))
                .build();
    }

    public static UserResponse toDTO(User user) {
        return UserResponse.builder()
                .idUsuario(user.getId())
                .nombre(user.getNombre())
                .correo(user.getCorreo())
                .nombreRol(user.getNombreRol().name())
                .build();
    }

    public static List<UserResponse> toDTOList(List<User> users) {
        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }
}
