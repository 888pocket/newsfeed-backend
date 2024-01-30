package com.joosangah.ordersystem.interaction.repository;

import com.joosangah.ordersystem.interaction.domain.dto.response.InteractionResponse;
import com.joosangah.ordersystem.user.domain.enums.InteractionType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

@RequiredArgsConstructor
public class InteractionCustomRepositoryImpl implements InteractionCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<InteractionResponse> filterByUserId(String userId) {

        // 1. 지정된 user_id와 InteractionType FOLLOW를 가진 interactions 필터링
        AggregationOperation matchStage = Aggregation.match(
                Criteria.where("user_id").is(userId)
                        .and("type").is(InteractionType.FOLLOW)
        );

        // 2. target_id가 user_id와 일치하는 interactions 조회
        LookupOperation lookupStage = LookupOperation.newLookup()
                .from("interactions")
                .localField("target_id")
                .foreignField("user_id")
                .as("followedInteractions");

        // 3. projection
        AggregationOperation projectStage = Aggregation.project()
                .and("followedInteractions.id").as("id")
                .and("followedInteractions.type").as("type")
                .and("followedInteractions.user_id").as("userId")
                .and("followedInteractions.created_at").as("createdAt")
                .and("followedInteractions.description").as("description");

        // followedInteractions를 풀어주는 unwind 단계 추가
        AggregationOperation unwindStage = Aggregation.unwind("followedInteractions");

        Aggregation aggregation = Aggregation.newAggregation(matchStage, lookupStage, unwindStage, projectStage);

        List<InteractionResponse> interactionResponses = mongoTemplate
                .aggregate(aggregation, "interactions", InteractionResponse.class)
                .getMappedResults();

        return interactionResponses;
    }
}
