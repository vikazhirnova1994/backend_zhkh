package com.example.backendhome.mapper;

import com.example.backendhome.dto.request.UserRequestDto;
import com.example.backendhome.dto.request.SignupRequest;
import com.example.backendhome.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class  UserMapper {

    @Autowired
    public PasswordEncoder encoder;

    @Mapping(target = "password", expression = "java(encoder.encode(signupRequest.getPassword()))")
    public abstract  User toUser(SignupRequest signupRequest);

    @Mapping(target = "password", expression = "java(encoder.encode(userDto.getPassword()))")
    public abstract  User toUser(UserRequestDto userDto);
}
