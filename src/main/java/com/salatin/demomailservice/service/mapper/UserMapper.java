package com.salatin.demomailservice.service.mapper;

import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.model.dto.request.UserCreateRequestDto;
import com.salatin.demomailservice.model.dto.request.UserUpdateRequestDto;
import com.salatin.demomailservice.model.dto.response.UserResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Mapper(componentModel = "spring")
public interface UserMapper extends DateTimeProvider{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", expression = "java(getCurrentDateTime())")
    User toModel(UserCreateRequestDto requestDto);

    @Mapping(target = "createdOn", ignore = true)
    User toModel(UserUpdateRequestDto requestDto);

    @Mapping(
        target = "createdOn",
        source = "createdOn",
        qualifiedByName = "formatDateTimeToString"
    )
    UserResponseDto toDto(User user);

    default Page<UserResponseDto> toPageDto(Page<User> userPage) {
        List<UserResponseDto> userDtoList = userPage.getContent().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        return new PageImpl<>(userDtoList, userPage.getPageable(), userPage.getTotalElements());
    }
}
