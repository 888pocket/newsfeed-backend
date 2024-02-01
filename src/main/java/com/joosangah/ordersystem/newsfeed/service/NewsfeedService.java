package com.joosangah.ordersystem.newsfeed.service;

import com.joosangah.ordersystem.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.ordersystem.newsfeed.domain.entity.Newsfeed;
import com.joosangah.ordersystem.newsfeed.domain.enums.AgentType;
import com.joosangah.ordersystem.newsfeed.domain.enums.NewsfeedType;
import com.joosangah.ordersystem.newsfeed.mapper.NewsfeedResponseMapper;
import com.joosangah.ordersystem.newsfeed.repository.NewsfeedRepository;
import com.joosangah.ordersystem.post.domain.entity.Post;
import com.joosangah.ordersystem.user.domain.entity.User;
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
                newsfeedRepository.findAllByUserOrderByCreatedAtDesc(user));
    }

    @Transactional
    public void addFollowNews(User follower, User follow) {
        // 해당 사용자를 팔로우 하는 사람에 알림
        List<Newsfeed> newNewsfeeds = new ArrayList<>();
        for (User user : follower.getFollowerList()) {
            newNewsfeeds.add(Newsfeed.builder()
                    .user(user)
                    .sender(follower)
                    .type(NewsfeedType.FOLLOW)
                    .agentType(AgentType.SENDER).build());
        }

        // 해당 사용자가 팔로우 하는 사람에 알림
        newNewsfeeds.add(Newsfeed.builder()
                .user(follow)
                .sender(follower)
                .type(NewsfeedType.FOLLOW)
                .agentType(AgentType.SENDER).build());

        newsfeedRepository.saveAll(newNewsfeeds);
    }

    @Transactional
    public void addPostNews(User user, Post post) {
        // 해당 사용자를 팔로우 하는 사람에 알림
        List<Newsfeed> newNewsfeeds = new ArrayList<>();
        for (User follower : user.getFollowerList()) {
            newNewsfeeds.add(Newsfeed.builder()
                    .user(follower)
                    .sender(user)
                    .type(NewsfeedType.POST)
                    .agentType(AgentType.SENDER)
                    .post(post).build());
        }

        newsfeedRepository.saveAll(newNewsfeeds);
    }

    @Transactional
    public void addCommentNews(User user, Post post) {
        // 해당 사용자를 팔로우 하는 사람에 알림
        List<Newsfeed> newNewsfeeds = new ArrayList<>();
        for (User follower : user.getFollowerList()) {
            newNewsfeeds.add(Newsfeed.builder()
                    .user(follower)
                    .sender(user)
                    .type(NewsfeedType.COMMENT)
                    .agentType(AgentType.SENDER)
                    .post(post).build());
        }

        // 해당 포스트 게시자에 알림
        newNewsfeeds.add(Newsfeed.builder()
                .user(post.getUser())
                .sender(user)
                .type(NewsfeedType.COMMENT)
                .agentType(AgentType.SENDER)
                .post(post).build());

        newsfeedRepository.saveAll(newNewsfeeds);
    }

    @Transactional
    public void addLikeNews(User user, Post post) {
        // 해당 사용자를 팔로우 하는 사람에 알림
        List<Newsfeed> newNewsfeeds = new ArrayList<>();
        for (User follower : user.getFollowerList()) {
            newNewsfeeds.add(Newsfeed.builder()
                    .user(follower)
                    .sender(user)
                    .type(NewsfeedType.LIKE_POST)
                    .agentType(AgentType.SENDER)
                    .post(post).build());
        }

        // 해당 포스트/댓글 게시자에 알림
        newNewsfeeds.add(Newsfeed.builder()
                .user(post.getUser())
                .sender(user)
                .type(NewsfeedType.LIKE_POST)
                .agentType(AgentType.USER)
                .post(post).build());

        newsfeedRepository.saveAll(newNewsfeeds);
    }
}
