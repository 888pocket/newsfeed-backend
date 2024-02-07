package com.joosangah.activityservice.comment.service;

import com.joosangah.activityservice.NewsfeedFeignClient;
import com.joosangah.activityservice.comment.domain.dto.request.CommentForm;
import com.joosangah.activityservice.comment.domain.entity.Comment;
import com.joosangah.activityservice.comment.repository.CommentRepository;
import com.joosangah.activityservice.common.domain.User;
import com.joosangah.activityservice.post.domain.entity.Post;
import com.joosangah.activityservice.post.service.PostService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostService postService;
    private final NewsfeedFeignClient newsfeedFeignClient;

    private final CommentRepository commentRepository;

    public Comment loadComment(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void addComment(User user, String targetId, CommentForm commentForm) {
        Comment newComment = Comment.builder()
                .userId(user.getId())
                .content(commentForm.getContent())
                .targetId(targetId)
                .deleted(false).build();
        commentRepository.save(newComment);

        Post findPost = postService.loadPost(targetId);
        findPost.getCommentList().add(newComment);

        postService.savePost(findPost);

        newsfeedFeignClient.addCommentNews(findPost);
    }
}
