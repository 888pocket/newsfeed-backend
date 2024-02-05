package com.joosangah.userservice.auth.repository;

import com.joosangah.userservice.auth.domain.entity.Role;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(String name);
}
