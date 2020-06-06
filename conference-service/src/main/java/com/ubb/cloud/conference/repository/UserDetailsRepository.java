package com.ubb.cloud.conference.repository;

import com.ubb.cloud.conference.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

    Optional<UserDetails> findById(Integer id);

    UserDetails findUserDetailsByFirstNameAndLastName(String firstName, String lastName);

    void deleteById(Integer id);
}