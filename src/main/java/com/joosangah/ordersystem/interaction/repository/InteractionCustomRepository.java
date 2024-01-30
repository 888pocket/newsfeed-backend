package com.joosangah.ordersystem.interaction.repository;

import com.joosangah.ordersystem.interaction.domain.dto.response.InteractionResponse;
import java.util.List;

public interface InteractionCustomRepository {

    List<InteractionResponse> filterByUserId(String userId);
}
