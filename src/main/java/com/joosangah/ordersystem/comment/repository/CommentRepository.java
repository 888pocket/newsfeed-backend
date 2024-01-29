package com.joosangah.ordersystem.comment.repository;

import com.joosangah.ordersystem.comment.domain.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

}
