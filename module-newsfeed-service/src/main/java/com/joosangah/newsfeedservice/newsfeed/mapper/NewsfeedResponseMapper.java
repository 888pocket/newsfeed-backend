package com.joosangah.newsfeedservice.newsfeed.mapper;

import com.joosangah.newsfeedservice.common.util.GenericMapper;
import com.joosangah.newsfeedservice.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.newsfeedservice.newsfeed.domain.entity.Newsfeed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsfeedResponseMapper extends GenericMapper<NewsfeedResponse, Newsfeed> {

}
