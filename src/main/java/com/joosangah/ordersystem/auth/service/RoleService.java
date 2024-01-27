package com.joosangah.ordersystem.auth.service;

import com.joosangah.ordersystem.auth.domain.entity.Role;
import com.joosangah.ordersystem.auth.repository.RoleRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role loadRole(String name) {
        return roleRepository.findByName(name).orElseThrow(NoSuchElementException::new);
    }
}
