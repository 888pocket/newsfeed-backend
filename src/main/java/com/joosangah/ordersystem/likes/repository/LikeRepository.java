package com.joosangah.ordersystem.likes.repository;

import com.joosangah.ordersystem.likes.domain.entity.Like;
import com.joosangah.ordersystem.likes.domain.enums.LikeType;
import com.joosangah.ordersystem.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {

    Optional<Like> findByUserAndTargetUserAndType(User user, User targetUser, LikeType type);
}
