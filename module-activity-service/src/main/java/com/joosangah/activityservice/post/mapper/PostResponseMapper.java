package com.joosangah.activityservice.post.mapper;

import com.joosangah.activityservice.common.util.GenericMapper;
import com.joosangah.activityservice.post.domain.dto.response.PostResponse;
import com.joosangah.activityservice.post.domain.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostResponseMapper extends GenericMapper<PostResponse, Post> {

}
