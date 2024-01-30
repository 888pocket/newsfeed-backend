package com.joosangah.ordersystem.interaction.service;

import com.joosangah.ordersystem.comment.service.CommentService;
import com.joosangah.ordersystem.interaction.domain.dto.response.InteractionResponse;
import com.joosangah.ordersystem.interaction.domain.entity.Interaction;
import com.joosangah.ordersystem.interaction.repository.InteractionRepository;
import com.joosangah.ordersystem.post.service.PostService;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.domain.enums.InteractionType;
import com.joosangah.ordersystem.user.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    private final InteractionRepository interactionRepository;

    @Transactional
    public void toggle(User user, String targetId, InteractionType type) {
        Optional<Interaction> findInteraction = interactionRepository.findByUserIdAndTargetIdAndType(
                user.getId(), targetId, type);
        if (findInteraction.isPresent()) {
            interactionRepository.delete(findInteraction.get());
            return;
        }

        interactionRepository.save(Interaction.builder()
                .userId(user.getId())
                .targetId(targetId)
                .type(type)
                .description(String.format("%s님이 %s", user.getName(), verifyTarget(targetId, type)))
                .build());
    }

    private String verifyTarget(String targetId, InteractionType type) {
        switch (type) {
            case FOLLOW -> {
                return String.format("%s님을 팔로우 합니다.", userService.loadUser(targetId).getName());
            }
            case LIKE_POST -> {
                return String.format("%s님의 글을 좋아합니다.", postService.loadPost(targetId).getUser().getName());
            }
            case LIKE_COMMENT -> {
                return String.format("%s님의 댓글을 좋아합니다.", commentService.loadComment(targetId).getUser().getName());
            }
        }

        return "";
    }

    public List<InteractionResponse> loadNewsfeeds(User user) {
        return interactionRepository.filterByUserId(user.getId());
    }
}
