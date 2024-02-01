package com.joosangah.ordersystem.newsfeed.domain.entity;

import com.joosangah.ordersystem.common.domain.AuditEntity;
import com.joosangah.ordersystem.newsfeed.domain.enums.AgentType;
import com.joosangah.ordersystem.newsfeed.domain.enums.NewsfeedType;
import com.joosangah.ordersystem.post.domain.entity.Post;
import com.joosangah.ordersystem.user.domain.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "newsfeeds")
public class Newsfeed extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @DBRef
    private User user;
    @DBRef
    private User sender;
    private NewsfeedType type;
    private AgentType agentType;
    @DBRef
    private Post post;

    @Builder
    public Newsfeed(User user, User sender, NewsfeedType type, AgentType agentType,
            Post post) {
        this.user = user;
        this.sender = sender;
        this.type = type;
        this.agentType = agentType;
        this.post = post;
    }
}
