package com.joosangah.activityservice.comment.domain.entity;

import com.joosangah.activityservice.common.domain.AuditEntity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "comments")
@Getter
public class Comment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String userId;
    @Field("target_id")
    private String targetId;
    private String content;
    @Field("is_deleted")
    private boolean deleted;

    @Builder
    public Comment(String userId, String targetId, String content, boolean deleted) {
        this.userId = userId;
        this.targetId = targetId;
        this.content = content;
        this.deleted = deleted;
    }
}
