package com.joosangah.activityservice.comment.repository;

import com.joosangah.activityservice.comment.domain.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

}
