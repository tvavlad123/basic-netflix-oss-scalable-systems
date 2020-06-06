package com.ubb.cloud.security;

import com.ubb.cloud.security.config.RoleKey;
import com.ubb.cloud.security.model.Role;
import com.ubb.cloud.security.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository) {
        return args -> {
            if (!roleRepository.existsByRoleName(RoleKey.ROLE_ADMIN.getKey())) {
                log.info("Preloading " + roleRepository.save(new Role(RoleKey.ROLE_ADMIN.getKey())));
            }
            if (!roleRepository.existsByRoleName(RoleKey.ROLE_PARTICIPANT.getKey())) {
                log.info("Preloading " + roleRepository.save(new Role(RoleKey.ROLE_PARTICIPANT.getKey())));
            }
            if (!roleRepository.existsByRoleName(RoleKey.ROLE_SPEAKER.getKey())) {
                log.info("Preloading " + roleRepository.save(new Role(RoleKey.ROLE_SPEAKER.getKey())));
            }
        };
    }
}