package com.joosangah.ordersystem.likes.domain.entity;

import com.joosangah.ordersystem.common.domain.AuditEntity;
import com.joosangah.ordersystem.likes.domain.enums.LikeType;
import com.joosangah.ordersystem.user.domain.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "likes")
public class Like extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @DBRef
    private User user;
    @DBRef
    @Field("target_user")
    private User targetUser;
    @Enumerated(EnumType.STRING)
    private LikeType type;

    @Builder
    public Like(User user, User targetUser, LikeType type) {
        this.user = user;
        this.targetUser = targetUser;
        this.type = type;
    }
}
