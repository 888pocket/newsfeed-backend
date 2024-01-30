package com.joosangah.ordersystem.interaction.domain.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InteractionResponse {

    private String id;
    private String type;
    private String userId;
    private LocalDateTime createdAt;
    private String description;
}
