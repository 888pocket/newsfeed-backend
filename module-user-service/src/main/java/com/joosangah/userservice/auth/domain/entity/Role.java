package com.joosangah.userservice.auth.domain.entity;

import com.joosangah.userservice.auth.domain.enums.ERole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
public class Role {

    @Id
    private String id;

    private ERole name;

    public ERole getName() {
        return name;
    }
}