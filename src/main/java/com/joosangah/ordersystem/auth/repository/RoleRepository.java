package com.joosangah.ordersystem.auth.repository;

import com.joosangah.ordersystem.auth.domain.entity.Role;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByName(String name);
}
