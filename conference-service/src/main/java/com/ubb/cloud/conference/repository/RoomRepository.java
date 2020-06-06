package com.ubb.cloud.conference.repository;

import com.ubb.cloud.conference.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findFirstByTalks_startTimeGreaterThan(Timestamp endTime);

    Room findById(Integer id);
}