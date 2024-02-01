package com.joosangah.ordersystem.newsfeed.mapper;

import com.joosangah.ordersystem.common.util.GenericMapper;
import com.joosangah.ordersystem.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.ordersystem.newsfeed.domain.entity.Newsfeed;
import com.joosangah.ordersystem.post.mapper.PostSummaryResponseMapper;
import com.joosangah.ordersystem.user.mapper.UserResponseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserResponseMapper.class, PostSummaryResponseMapper.class})
public interface NewsfeedResponseMapper extends GenericMapper<NewsfeedResponse, Newsfeed> {

}
