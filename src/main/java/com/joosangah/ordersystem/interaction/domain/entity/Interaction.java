package com.joosangah.ordersystem.interaction.domain.entity;

import com.joosangah.ordersystem.common.domain.AuditEntity;
import com.joosangah.ordersystem.user.domain.enums.InteractionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "interactions")
public class Interaction extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Field("user_id")
    private String userId;
    @Field("target_id")
    private String targetId;
    @Enumerated(EnumType.STRING)
    private InteractionType type;

    private String description;

    @Builder
    public Interaction(String userId, String targetId, InteractionType type, String description) {
        this.userId = userId;
        this.targetId = targetId;
        this.type = type;
        this.description = description;
    }
}
