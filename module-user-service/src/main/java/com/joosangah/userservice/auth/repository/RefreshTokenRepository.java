package com.joosangah.userservice.auth.repository;

import com.joosangah.userservice.auth.domain.entity.RefreshToken;
import com.joosangah.userservice.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);

    @Modifying
    int deleteByToken(String token);
}
