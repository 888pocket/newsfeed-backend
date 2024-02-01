package com.joosangah.ordersystem.follow.domain;

import com.joosangah.ordersystem.common.domain.AuditEntity;
import com.joosangah.ordersystem.user.domain.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "follows")
public class Follow extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @DBRef
    private User user;
    @DBRef
    @Field("target_user")
    private User targetUser;

    @Builder
    public Follow(User user, User targetUser) {
        this.user = user;
        this.targetUser = targetUser;
    }
}
