package com.joosangah.ordersystem.comment.domain.entity;

import com.joosangah.ordersystem.common.domain.AuditEntity;
import com.joosangah.ordersystem.likes.domain.entity.Like;
import com.joosangah.ordersystem.user.domain.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;
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
    @Field("target_user")
    @DBRef
    private User targetUser;
    private String content;
    @Field("is_deleted")
    private boolean deleted;

    @DBRef
    private List<Like> likeList;

    @Builder
    public Comment(User user, User targetUser, String content, boolean deleted) {
        this.user = user;
        this.targetUser = targetUser;
        this.content = content;
        this.deleted = deleted;
        this.likeList = new ArrayList<>();
    }
}
