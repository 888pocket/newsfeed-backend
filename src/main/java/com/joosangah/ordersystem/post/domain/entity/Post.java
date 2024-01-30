package com.joosangah.ordersystem.post.domain.entity;

import com.joosangah.ordersystem.comment.domain.entity.Comment;
import com.joosangah.ordersystem.common.domain.AuditEntity;
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

@Document(collection = "posts")
@Getter
public class Post extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @DBRef
    private User user;
    private String title;
    private String content;
    @Field("is_deleted")
    private boolean deleted;

    @DBRef
    private List<Comment> commentList;
    @DBRef
    private List<User> likeUserList;

    @Builder
    public Post(User user, String title, String content, boolean deleted) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.deleted = deleted;
        this.commentList = new ArrayList<>();
        this.likeUserList = new ArrayList<>();
    }
}
