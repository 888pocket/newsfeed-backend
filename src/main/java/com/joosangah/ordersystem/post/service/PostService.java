package com.joosangah.ordersystem.post.service;

import com.joosangah.ordersystem.post.domain.dto.request.PostForm;
import com.joosangah.ordersystem.post.domain.dto.response.PostResponse;
import com.joosangah.ordersystem.post.domain.entity.Post;
import com.joosangah.ordersystem.post.mapper.PostResponseMapper;
import com.joosangah.ordersystem.post.repository.PostRepository;
import com.joosangah.ordersystem.user.domain.entity.User;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MongoTemplate mongoTemplate;

    private final PostResponseMapper postResponseMapper;

    private final PostRepository postRepository;

    public Post loadPost(String postId) {
        return postRepository.findByIdAndDeletedIsFalse(postId)
                .orElseThrow(NoSuchElementException::new);
    }

    public PostResponse getPost(String postId) {
        return postResponseMapper.toDto(loadPost(postId));
    }

    @Transactional
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public String addPost(User user, PostForm postForm) {
        Post newPost = Post.builder()
                .user(user)
                .title(postForm.getTitle())
                .content(postForm.getContent()).build();

        postRepository.save(newPost);

        return newPost.getId();
    }

    public boolean isAuthor(String userId, String postId) {
        return loadPost(postId).getUser().getId().equals(userId);
    }

    public void softDelete(String userId, String postId) {
        if (!isAuthor(userId, postId)) {
            throw new AccessDeniedException("access denied");
        }
        Query query = Query.query(Criteria.where("id").is(postId));
        Update update = Update.update("is_deleted", true);
        mongoTemplate.updateFirst(query, update, Post.class);
    }
}
