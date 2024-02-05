package com.joosangah.activityservice.common.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
public class AuditEntity {

    @Field("created_by")
    @CreatedBy
    private String createdBy;

    @Field("created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field("updated_by")
    @LastModifiedBy
    private String updatedBy;

    @Field("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
