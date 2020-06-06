package com.ubb.cloud.conference.repository;

import com.ubb.cloud.conference.model.Talk;
import com.ubb.cloud.conference.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TalkRepository extends JpaRepository<Talk, Integer> {

    List<Talk> findByAttendeesContaining(UserDetails attendee);

    List<Talk> findByAttendeesNotContaining(UserDetails attendee);

    List<Talk> findAll();

    Optional<Talk> findById(Integer id);
}