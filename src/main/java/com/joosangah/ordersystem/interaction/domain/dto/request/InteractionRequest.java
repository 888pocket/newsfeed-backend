package com.joosangah.ordersystem.interaction.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InteractionRequest {

    @NotBlank
    private String targetId;
}
