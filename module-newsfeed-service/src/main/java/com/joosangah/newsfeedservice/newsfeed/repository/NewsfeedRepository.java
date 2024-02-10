package com.joosangah.newsfeedservice.newsfeed.repository;

import com.joosangah.newsfeedservice.newsfeed.domain.entity.Newsfeed;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsfeedRepository extends MongoRepository<Newsfeed, String> {

    List<Newsfeed> findAllByUserIdOrderByCreatedAtDesc(String userId);
}
