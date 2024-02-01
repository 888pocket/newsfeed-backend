package com.joosangah.ordersystem.comment.service;

import com.joosangah.ordersystem.comment.domain.dto.request.CommentForm;
import com.joosangah.ordersystem.comment.domain.entity.Comment;
import com.joosangah.ordersystem.comment.repository.CommentRepository;
import com.joosangah.ordersystem.post.domain.entity.Post;
import com.joosangah.ordersystem.post.service.PostService;
import com.joosangah.ordersystem.user.domain.entity.User;
import com.joosangah.ordersystem.user.service.UserService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final UserService userService;

    private final CommentRepository commentRepository;

    public Comment loadComment(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void addComment(User user, String targetId, CommentForm commentForm) {
        Post findPost = postService.loadPost(targetId);
        Comment newComment = Comment.builder()
                .user(user)
                .content(commentForm.getContent())
                .targetUser(findPost.getUser())
                .deleted(false).build();
        commentRepository.save(newComment);

        findPost.getCommentList().add(newComment);

        postService.savePost(findPost);
    }
}
