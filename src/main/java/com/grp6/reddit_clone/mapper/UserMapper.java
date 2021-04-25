package com.grp6.reddit_clone.mapper;

import com.grp6.reddit_clone.dto.UserDto;
import com.grp6.reddit_clone.model.User;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapUserToDto(User user);

    @InheritInverseConfiguration
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User mapDtoToUser(UserDto userDto);
}
