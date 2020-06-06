package com.ubb.cloud.conference.controller;

import com.ubb.cloud.conference.model.NewTalkDTO;
import com.ubb.cloud.conference.model.RoomDTO;
import com.ubb.cloud.conference.model.TalkDTO;
import com.ubb.cloud.conference.model.TalkDetailDTO;
import com.ubb.cloud.conference.model.TalkParticipantsDTO;
import com.ubb.cloud.conference.payload.RegistrationRequest;
import com.ubb.cloud.conference.service.TalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/conference/talks")
public class TalkController {

    private final TalkService talkService;

    @Autowired
    public TalkController(TalkService talkService) {
        this.talkService = talkService;
    }

    @RequestMapping(value = "/attendee/{userId}", method = RequestMethod.GET)
    public List<TalkDTO> findTalksForUser(@PathVariable Integer userId) {
        return talkService.getTalksByUserId(userId);
    }

    @RequestMapping(value = "/available", method = RequestMethod.GET)
    public List<TalkDTO> findTalks(@RequestParam("userId") Integer userId ) {
        return talkService.getTalksAvailable(userId);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<TalkDetailDTO> findAllTalks(){
        return talkService.getAllTalks();
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void registerForTalk(@RequestBody RegistrationRequest registrationRequest) {
        talkService.registerForTalk(registrationRequest.getUserId(),registrationRequest.getTalkId());
    }

    @RequestMapping(value = "/unregister", method = RequestMethod.POST)
    public void unregisterFromTalk(@RequestBody RegistrationRequest registrationRequest) {
        talkService.unregisterFromTalk(registrationRequest.getUserId(),registrationRequest.getTalkId());
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public List<TalkParticipantsDTO> getTalks() {
        return talkService.getTalksStatistics();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RoomDTO createTalk(@RequestBody NewTalkDTO newTalkDTO) {
        return talkService.createTalk(newTalkDTO);
    }
}