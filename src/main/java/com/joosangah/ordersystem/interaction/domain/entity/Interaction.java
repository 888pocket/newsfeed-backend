package com.joosangah.ordersystem.interaction.domain.entity;

import com.joosangah.ordersystem.user.domain.enums.InteractionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "interactions")
@CompoundIndexes({
        @CompoundIndex(name = "compound_key_index", def = "{'userId' : 1, 'targetId' : 1, 'type' : 1}", unique = true)
})
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Field("user_id")
    private String userId;
    @Field("target_id")
    private String targetId;
    @Enumerated(EnumType.STRING)
    private InteractionType type;
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Interaction(String userId, String targetId, InteractionType type) {
        this.userId = userId;
        this.targetId = targetId;
        this.type = type;
    }
}
