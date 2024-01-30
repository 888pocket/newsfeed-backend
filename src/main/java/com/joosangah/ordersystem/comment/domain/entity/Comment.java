package com.joosangah.ordersystem.comment.domain.entity;

import com.joosangah.ordersystem.common.domain.AuditEntity;
import com.joosangah.ordersystem.user.domain.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "comments")
@Getter
public class Comment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @DBRef
    private User user;
    @Field("target_id")
    private String targetId;
    private String content;
    @Field("is_deleted")
    private boolean deleted;

    @Builder
    public Comment(User user, String targetId, String content, boolean deleted) {
        this.user = user;
        this.targetId = targetId;
        this.content = content;
        this.deleted = deleted;
    }
}
