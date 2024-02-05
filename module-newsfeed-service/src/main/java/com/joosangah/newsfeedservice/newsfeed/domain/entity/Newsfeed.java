package com.joosangah.newsfeedservice.newsfeed.domain.entity;

import com.joosangah.newsfeedservice.common.domain.AuditEntity;
import com.joosangah.newsfeedservice.newsfeed.domain.enums.AgentType;
import com.joosangah.newsfeedservice.newsfeed.domain.enums.NewsfeedType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "newsfeeds")
public class Newsfeed extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String userId;
    private String senderId;
    private NewsfeedType type;
    private AgentType agentType;
    private String postId;

    @Builder
    public Newsfeed(String userId, String senderId, NewsfeedType type, AgentType agentType,
            String postId) {
        this.userId = userId;
        this.senderId = senderId;
        this.type = type;
        this.agentType = agentType;
        this.postId = postId;
    }
}
