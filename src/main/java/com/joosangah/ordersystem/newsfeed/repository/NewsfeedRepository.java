package com.joosangah.ordersystem.newsfeed.repository;

import com.joosangah.ordersystem.newsfeed.domain.entity.Newsfeed;
import com.joosangah.ordersystem.user.domain.entity.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedRepository extends MongoRepository<Newsfeed, String> {

    List<Newsfeed> findAllByUserOrderByCreatedAtDesc(User user);
}
