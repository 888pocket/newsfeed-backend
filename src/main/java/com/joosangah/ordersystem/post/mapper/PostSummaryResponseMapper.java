package com.joosangah.ordersystem.post.mapper;

import com.joosangah.ordersystem.common.util.GenericMapper;
import com.joosangah.ordersystem.post.domain.dto.response.PostSummaryResponse;
import com.joosangah.ordersystem.post.domain.entity.Post;
import com.joosangah.ordersystem.user.mapper.UserResponseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserResponseMapper.class)
public interface PostSummaryResponseMapper extends GenericMapper<PostSummaryResponse, Post> {

}
