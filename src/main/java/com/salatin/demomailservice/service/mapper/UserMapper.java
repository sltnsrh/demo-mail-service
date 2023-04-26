package com.salatin.demomailservice.service.mapper;

import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.model.dto.request.UserCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.UserUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.UserResponseDto;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", expression = "java(getCurrentTime())")
    User toModel(UserCreateRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    User toModel(UserUpdateRequestDto requestDto);

    UserResponseDto toDto(User user);

    default LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }
}
