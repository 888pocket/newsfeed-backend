package com.joosangah.activityservice.post.service;

import com.joosangah.activityservice.common.client.NewsfeedFeignClient;
import com.joosangah.activityservice.common.domain.User;
import com.joosangah.activityservice.post.domain.dto.request.PostForm;
import com.joosangah.activityservice.post.domain.dto.response.PostResponse;
import com.joosangah.activityservice.post.domain.entity.Post;
import com.joosangah.activityservice.post.mapper.PostResponseMapper;
import com.joosangah.activityservice.post.repository.PostRepository;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MongoTemplate mongoTemplate;

    private final NewsfeedFeignClient newsfeedFeignClient;

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
                .userId(user.getId())
                .title(postForm.getTitle())
                .content(postForm.getContent())
                .deleted(false).build();

        postRepository.save(newPost);

        newsfeedFeignClient.addPostNews(newPost);

        return newPost.getId();
    }

    public boolean isAuthor(String userId, String postId) {
        return loadPost(postId).getUserId().equals(userId);
    }

    public void softDelete(String userId, String postId) throws AccessDeniedException {
        if (!isAuthor(userId, postId)) {
            throw new AccessDeniedException("access denied");
        }
        Query query = Query.query(Criteria.where("id").is(postId));
        Update update = Update.update("is_deleted", true);
        mongoTemplate.updateFirst(query, update, Post.class);
    }

    @Transactional
    public boolean toggleLike(String userId, String postId) {
        Post findPost = loadPost(postId);
        if (findPost.getLikeUserIdList().contains(userId)) {
            findPost.getLikeUserIdList().remove(userId);
            postRepository.save(findPost);
            return false;
        }

        findPost.getLikeUserIdList().add(userId);
        postRepository.save(findPost);

        newsfeedFeignClient.addLikeNews(findPost);

        return true;
    }
}
