package com.ubb.cloud.security.repository;

import com.ubb.cloud.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}