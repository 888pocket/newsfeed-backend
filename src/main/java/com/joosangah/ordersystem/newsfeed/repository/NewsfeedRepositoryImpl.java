package com.joosangah.ordersystem.newsfeed.repository;

import com.joosangah.ordersystem.newsfeed.domain.dto.response.NewsfeedResponse;
import com.joosangah.ordersystem.user.domain.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NewsfeedRepositoryImpl implements NewsfeedCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<NewsfeedResponse> findAllBySearchParam(User user) {
        // 팔로잉의 좋아요
        // 나를 팔로우 한 사람
        // 내 포스트에 좋아요 활동
        // 내 포스트에 댓글

        Aggregation aggregation = Aggregation.newAggregation(
                getFollowingUsers(user),
//                getPosts(),
//                Aggregation.unwind("postLookup"),
//                projectPosts(),
                tempProject());

        List<NewsfeedResponse> newsfeedResponses = mongoTemplate
                .aggregate(aggregation, "follows", NewsfeedResponse.class)
                .getMappedResults();

        return newsfeedResponses;
    }

    private AggregationOperation getFollowingUsers(User user) {
        // 팔로잉 유저
        return Aggregation.match(
                Criteria.where("targetUser.id").is(user.getId())
        );
    }

    private AggregationOperation tempProject() {
        return Aggregation.project()
                .and("user.id").as("userId")
                .and("user.name").as("userName")
                .and("target_user.id").as("targetId")
                .and("targetUser.name").as("targetName");
    }

    private LookupOperation getPosts() {
        // 팔로잉의 포스트
        return LookupOperation.newLookup()
                .from("posts")
                .localField("target_user.id")
                .foreignField("user.id")
                .as("postLookup");
    }

    private AggregationOperation matchPosts() {
        return Aggregation.match(
                Criteria.where("user").is("user")
        );
    }

    private AggregationOperation projectPosts() {
        return Aggregation.project()
                .and("postLookup.user.id").as("userId")
                .and("postLookup.user.name").as("userName")
                .and("postLookup.id").as("targetId")
                .and("postLookup.title").as("targetName")
                .and("postLookup.created_at").as("createdAt");
    }

    private LookupOperation getComments() {
        // 팔로잉의 댓글
        return LookupOperation.newLookup()
                .from("comments")
                .localField("target_user")
                .foreignField("user")
                .as("commentLookup");
    }

    private AggregationOperation filterComments() {
        return Aggregation.project()
//                .and("commentLookup.user.id").as("userId")
//                .and("commentLookup.user.name").as("userName")
                .and("commentLookup.id").as("targetId")
                .and("commentLookup.created_at").as("createdAt");
    }

    private LookupOperation getFollows() {
        // 팔로잉의 팔로우
        return LookupOperation.newLookup()
                .from("follows")
                .localField("user")
                .foreignField("target_user")
                .as("followLookup");
    }

    private AggregationOperation matchFollows() {
        return Aggregation.match(
                Criteria.where("user").is("user")
        );
    }

    private AggregationOperation projectFollows() {
        return Aggregation.project()
                .and("followLookup.user.id").as("userId")
                .and("followLookup.user.name").as("userName")
                .and("followLookup.id").as("targetId")
                .and("followLookup.created_at").as("createdAt");
    }
}
