package com.joosangah.userservice.user.mapper;

import com.joosangah.userservice.common.util.GenericMapper;
import com.joosangah.userservice.user.domain.dto.response.UserResponse;
import com.joosangah.userservice.user.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper extends GenericMapper<UserResponse, User> {

}
