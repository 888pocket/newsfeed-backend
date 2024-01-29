package com.joosangah.ordersystem.interaction.repository;

import com.joosangah.ordersystem.interaction.domain.entity.Interaction;
import com.joosangah.ordersystem.user.domain.enums.InteractionType;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends MongoRepository<Interaction, String> {

    Optional<Interaction> findByUserIdAndTargetIdAndType(String userId, String targetId,
            InteractionType type);
}
