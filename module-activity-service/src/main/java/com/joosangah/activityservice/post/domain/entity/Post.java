package com.joosangah.activityservice.post.domain.entity;

import com.joosangah.activityservice.comment.domain.entity.Comment;
import com.joosangah.activityservice.common.domain.AuditEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    private String userId;
    private String title;
    private String content;
    @Field("is_deleted")
    private boolean deleted;

    @DBRef
    private List<Comment> commentList;
    private List<String> likeUserIdList;

    @Builder
    public Post(String userId, String title, String content, boolean deleted) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.deleted = deleted;
        this.commentList = new ArrayList<>();
        this.likeUserIdList = new ArrayList<>();
    }
}
