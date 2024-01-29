package com.joosangah.ordersystem.auth.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document("blacklist")
public class BlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String userId;
    @Indexed(expireAfterSeconds = 3_024_000)   // 35일
    private Instant expiryDate;
    @CreatedDate
    private LocalDateTime createdAt;
}
