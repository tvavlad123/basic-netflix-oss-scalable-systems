package com.ubb.cloud.conference;

import com.ubb.cloud.conference.model.Room;
import com.ubb.cloud.conference.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.IntStream;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(RoomRepository roomRepository) {
        return args -> {
            IntStream.range(0, 10).forEach(index -> roomRepository.save(new Room(index, String.format("Room_%s", index), 50)));
        };
    }
}