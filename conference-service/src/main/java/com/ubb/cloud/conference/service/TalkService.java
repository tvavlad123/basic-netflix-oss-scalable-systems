package com.ubb.cloud.conference.service;

import com.ubb.cloud.conference.model.Article;
import com.ubb.cloud.conference.model.NewTalkDTO;
import com.ubb.cloud.conference.model.Room;
import com.ubb.cloud.conference.model.RoomDTO;
import com.ubb.cloud.conference.model.Talk;
import com.ubb.cloud.conference.model.TalkDTO;
import com.ubb.cloud.conference.model.TalkDetailDTO;
import com.ubb.cloud.conference.model.TalkParticipantsDTO;
import com.ubb.cloud.conference.model.UserDetails;
import com.ubb.cloud.conference.repository.ArticleRepository;
import com.ubb.cloud.conference.repository.RoomRepository;
import com.ubb.cloud.conference.repository.TalkRepository;
import com.ubb.cloud.conference.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class TalkService {

    private final TalkRepository talkRepository;

    private final UserDetailsRepository userDetailsRepository;

    private final RoomRepository roomRepository;

    private final ArticleRepository articleRepository;

    @Autowired
    public TalkService(TalkRepository talkRepository, UserDetailsRepository userDetailsRepository, RoomRepository roomRepository,
                       ArticleRepository articleRepository) {
        this.talkRepository = talkRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.roomRepository = roomRepository;
        this.articleRepository = articleRepository;
    }

    public List<TalkDTO> getTalksByUserId(Integer userId) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(userId);
        return talkRepository.findByAttendeesContaining(userDetails).stream().map(Talk::toTalkDTO).collect(Collectors.toList());
    }

    public List<TalkDTO> getTalksAvailable(Integer userId) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(userId);
        return talkRepository.findByAttendeesNotContaining(userDetails).stream().map(Talk::toTalkDTO).collect(Collectors.toList());
    }

    public List<TalkDetailDTO> getAllTalks() {
        return talkRepository.findAll().stream().map(Talk::toTalkDetailDTO).collect(Collectors.toList());
    }

    public void registerForTalk(Integer userId, Integer talkId) {
        Optional<UserDetails> user = userDetailsRepository.findById(userId);
        Optional<Talk> talk = talkRepository.findById(talkId);
        user.ifPresent(u -> talk.ifPresent(t -> {
            t.addAttendee(u);
            talkRepository.save(t);
        }));
    }

    public void unregisterFromTalk(Integer userId, Integer talkId) {
        Optional<UserDetails> user = userDetailsRepository.findById(userId);
        Optional<Talk> talk = talkRepository.findById(talkId);
        user.ifPresent(u -> talk.ifPresent(t -> {
            t.removeAttendee(u);
            talkRepository.save(t);
        }));
    }

    public RoomDTO createTalk(NewTalkDTO newTalkDTO) {
        Optional<Room> room = roomRepository.findFirstByTalks_startTimeGreaterThan(newTalkDTO.getEndTime());
        Optional<Article> article = articleRepository.findById(newTalkDTO.getArticleId());
        if (!room.isPresent()) {
            Random random = new Random();
            int randomIndex = random.nextInt((int) roomRepository.count());
            Room newRoom = roomRepository.findById(randomIndex);
            article.ifPresent(a -> buildTalk(newTalkDTO, newRoom, a));
            return newRoom.toRoomDTO();
        }
        return room.map(r -> {
            article.ifPresent(a -> buildTalk(newTalkDTO, r, a)
            );
            return r.toRoomDTO();
        }).orElseThrow(() -> new NoSuchElementException("No room found!"));
    }

    private void buildTalk(NewTalkDTO newTalkDTO, Room r, Article a) {
        Talk talk = new Talk();
        talk.setArticle(a);
        talk.setStartTime(newTalkDTO.getStartTime());
        talk.setEndTime(newTalkDTO.getEndTime());
        talk.setRoom(r);
        talkRepository.save(talk);
    }

    public List<TalkParticipantsDTO> getTalksStatistics() {
        return talkRepository.findAll().stream().map(t -> {
            String title = t.getArticle().getTitle();
            int numberOfParticipants = t.getAttendees().size();
            int places = t.getRoom().getPlaces();
            return new TalkParticipantsDTO(title, numberOfParticipants, places);
        }).collect(Collectors.toList());
    }
}