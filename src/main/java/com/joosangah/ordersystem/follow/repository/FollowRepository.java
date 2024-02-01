package com.joosangah.ordersystem.follow.repository;

import com.joosangah.ordersystem.follow.domain.Follow;
import com.joosangah.ordersystem.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends MongoRepository<Follow, String> {

    Optional<Follow> findByUserAndTargetUser(User user, User targetUser);
}
