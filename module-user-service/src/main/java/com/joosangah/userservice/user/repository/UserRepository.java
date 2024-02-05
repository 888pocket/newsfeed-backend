package com.joosangah.userservice.user.repository;

import com.joosangah.userservice.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findById(String id);

    Optional<User> findByEmail(String email);
}
