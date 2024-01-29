package com.joosangah.ordersystem.auth.repository;

import com.joosangah.ordersystem.auth.domain.entity.BlackList;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlackListRepository extends MongoRepository<BlackList, String> {

    Optional<BlackList> findByUserId(String userId);

    @Modifying
    int deleteByUserId(String userId);
}
