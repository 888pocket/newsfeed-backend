package com.joosangah.ordersystem.interaction.service;

import com.joosangah.ordersystem.interaction.domain.entity.Interaction;
import com.joosangah.ordersystem.interaction.repository.InteractionRepository;
import com.joosangah.ordersystem.post.service.PostService;
import com.joosangah.ordersystem.user.domain.enums.InteractionType;
import com.joosangah.ordersystem.user.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final UserService userService;
    private final PostService postService;

    private final InteractionRepository interactionRepository;

    @Transactional
    public void toggle(String userId, String targetId, InteractionType type) {
        Optional<Interaction> findInteraction = interactionRepository.findByUserIdAndTargetIdAndType(
                userId, targetId, type);
        if (findInteraction.isPresent()) {
            interactionRepository.delete(findInteraction.get());
            return;
        }

        verifyTarget(targetId, type);

        interactionRepository.save(Interaction.builder()
                .userId(userId)
                .targetId(targetId)
                .type(type).build());
    }

    private void verifyTarget(String targetId, InteractionType type) {
        switch (type) {
            case FOLLOW -> userService.loadUser(targetId);
            case LIKE_POST -> postService.loadPost(targetId);
            case LIKE_COMMENT -> {
            }
        }
    }
}
