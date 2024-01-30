package com.joosangah.ordersystem.comment.service;

import com.joosangah.ordersystem.comment.domain.dto.request.CommentForm;
import com.joosangah.ordersystem.comment.domain.entity.Comment;
import com.joosangah.ordersystem.comment.repository.CommentRepository;
import com.joosangah.ordersystem.interaction.domain.entity.Interaction;
import com.joosangah.ordersystem.interaction.repository.InteractionRepository;
import com.joosangah.ordersystem.post.domain.entity.Post;
import com.joosangah.ordersystem.post.service.PostService;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.domain.enums.InteractionType;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;

    private final CommentRepository commentRepository;
    private final InteractionRepository interactionRepository;

    public Comment loadComment(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void addComment(User user, String targetId, CommentForm commentForm) {
        Comment newComment = Comment.builder()
                .user(user)
                .content(commentForm.getContent())
                .targetId(targetId)
                .deleted(false).build();
        commentRepository.save(newComment);

        Post findPost = postService.loadPost(targetId);
        findPost.getCommentList().add(newComment);

        postService.savePost(findPost);

        interactionRepository.save(Interaction.builder()
                .userId(user.getId())
                .targetId(targetId)
                .type(InteractionType.COMMENT)
                .description(String.format("%s님이 %s님의 글에 댓글을 남겼습니다.", user.getName(),
                        newComment.getUser().getName()))
                .build());
    }
}
