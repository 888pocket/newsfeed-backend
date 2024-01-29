package com.joosangah.ordersystem.post.domain.entity;

import com.joosangah.ordersystem.comment.domain.entity.Comment;
import com.joosangah.ordersystem.user.domain.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @DBRef
    private User user;
    private String content;
    @Field("is_deleted")
    private boolean deleted;
    @Field("created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Field("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @DBRef
    private List<Comment> commentList;

    @Builder
    public Post(User user, String content, boolean deleted) {
        this.user = user;
        this.content = content;
        this.deleted = deleted;
        this.commentList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(
            List<Comment> commentList) {
        this.commentList = commentList;
    }
}
