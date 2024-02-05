package com.joosangah.userservice.auth.domain.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
