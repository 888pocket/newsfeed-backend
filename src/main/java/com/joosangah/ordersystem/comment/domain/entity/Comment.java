package com.joosangah.ordersystem.comment.domain.entity;

import com.joosangah.ordersystem.user.domain.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "comments")
public class Comment {

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
    @Field("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Field("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Comment(User user, String targetId, String content, boolean deleted) {
        this.user = user;
        this.targetId = targetId;
        this.content = content;
        this.deleted = deleted;
    }
}
