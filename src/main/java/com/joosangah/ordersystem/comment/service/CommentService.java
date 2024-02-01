package com.joosangah.ordersystem.comment.service;

import com.joosangah.ordersystem.comment.domain.dto.request.CommentForm;
import com.joosangah.ordersystem.comment.domain.entity.Comment;
import com.joosangah.ordersystem.comment.repository.CommentRepository;
import com.joosangah.ordersystem.newsfeed.service.NewsfeedService;
import com.joosangah.ordersystem.post.domain.entity.Post;
import com.joosangah.ordersystem.post.service.PostService;
import com.joosangah.ordersystem.user.domain.entity.User;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final NewsfeedService newsfeedService;

    private final CommentRepository commentRepository;

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

        newsfeedService.addCommentNews(user, findPost);
    }
}
