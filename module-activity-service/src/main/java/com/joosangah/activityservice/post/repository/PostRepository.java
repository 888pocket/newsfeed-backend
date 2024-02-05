package com.joosangah.activityservice.post.repository;

import com.joosangah.activityservice.post.domain.entity.Post;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Optional<Post> findByIdAndDeletedIsFalse(String postId);
}
