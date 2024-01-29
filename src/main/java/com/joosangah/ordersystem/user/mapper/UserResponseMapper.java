package com.joosangah.ordersystem.user.mapper;

import com.joosangah.ordersystem.common.util.GenericMapper;
import com.joosangah.ordersystem.user.domain.dto.response.UserResponse;
import com.joosangah.ordersystem.user.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResponseMapper extends GenericMapper<UserResponse, User> {

}
