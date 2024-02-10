package com.joosangah.newsfeedservice.newsfeed.service;

import com.joosangah.newsfeedservice.common.domain.Post;
import com.joosangah.newsfeedservice.common.domain.User;
import com.joosangah.newsfeedservice.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.newsfeedservice.newsfeed.domain.entity.Newsfeed;
import com.joosangah.newsfeedservice.newsfeed.domain.enums.AgentType;
import com.joosangah.newsfeedservice.newsfeed.domain.enums.NewsfeedType;
import com.joosangah.newsfeedservice.newsfeed.mapper.NewsfeedResponseMapper;
import com.joosangah.newsfeedservice.newsfeed.repository.NewsfeedRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;

    private final NewsfeedResponseMapper newsfeedResponseMapper;

    public List<NewsfeedResponse> loadNewsfeeds(User user) {
        return newsfeedResponseMapper.toDtoList(
                newsfeedRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId()));
    }

    @Transactional
    public void addFollowNews(User follower, User follow) {
        // 해당 사용자를 팔로우 하는 사람에 알림
        List<Newsfeed> newNewsfeeds = new ArrayList<>();
        for (String userId : follower.getFollowerIdList()) {
            newNewsfeeds.add(Newsfeed.builder()
                    .userId(userId)
                    .senderId(follower.getId())
                    .type(NewsfeedType.FOLLOW)
                    .agentType(AgentType.SENDER).build());
        }

        // 해당 사용자가 팔로우 하는 사람에 알림
        newNewsfeeds.add(Newsfeed.builder()
                .userId(follow.getId())
                .senderId(follower.getId())
                .type(NewsfeedType.FOLLOW)
                .agentType(AgentType.SENDER).build());

        newsfeedRepository.saveAll(newNewsfeeds);
    }

    @Transactional
    public void addPostNews(User user, Post post) {
        // 해당 사용자를 팔로우 하는 사람에 알림
        List<Newsfeed> newNewsfeeds = new ArrayList<>();
        for (String follower : user.getFollowerIdList()) {
            newNewsfeeds.add(Newsfeed.builder()
                    .userId(follower)
                    .senderId(user.getId())
                    .type(NewsfeedType.POST)
                    .agentType(AgentType.SENDER)
                    .postId(post.getId()).build());
        }

        newsfeedRepository.saveAll(newNewsfeeds);
    }

    @Transactional
    public void addCommentNews(User user, Post post) {
        // 해당 사용자를 팔로우 하는 사람에 알림
        List<Newsfeed> newNewsfeeds = new ArrayList<>();
        for (String follower : user.getFollowerIdList()) {
            newNewsfeeds.add(Newsfeed.builder()
                    .userId(follower)
                    .senderId(user.getId())
                    .type(NewsfeedType.COMMENT)
                    .agentType(AgentType.SENDER)
                    .postId(post.getId()).build());
        }

        // 해당 포스트 게시자에 알림
        newNewsfeeds.add(Newsfeed.builder()
                .userId(post.getUserId())
                .senderId(user.getId())
                .type(NewsfeedType.COMMENT)
                .agentType(AgentType.SENDER)
                .postId(post.getId()).build());

        newsfeedRepository.saveAll(newNewsfeeds);
    }

    @Transactional
    public void addLikeNews(User user, Post post) {
        // 해당 사용자를 팔로우 하는 사람에 알림
        List<Newsfeed> newNewsfeeds = new ArrayList<>();
        for (String follower : user.getFollowerIdList()) {
            newNewsfeeds.add(Newsfeed.builder()
                    .userId(follower)
                    .senderId(user.getId())
                    .type(NewsfeedType.LIKE_POST)
                    .agentType(AgentType.SENDER)
                    .postId(post.getId()).build());
        }

        // 해당 포스트/댓글 게시자에 알림
        newNewsfeeds.add(Newsfeed.builder()
                .userId(post.getUserId())
                .senderId(user.getId())
                .type(NewsfeedType.LIKE_POST)
                .agentType(AgentType.USER)
                .postId(post.getId()).build());

        newsfeedRepository.saveAll(newNewsfeeds);
    }
}
