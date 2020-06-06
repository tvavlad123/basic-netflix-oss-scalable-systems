package com.ubb.cloud.conference.controller;

import com.ubb.cloud.conference.model.UserDetails;
import com.ubb.cloud.conference.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api/conference/userdetails")
public class UserDetailsController {

    private final com.ubb.cloud.conference.service.UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsService UserDetailsService) {
        this.userDetailsService = UserDetailsService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveUserDetails(@RequestBody UserDetails UserDetails) {
        userDetailsService.saveUser(UserDetails);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDetails getUserDetails(@PathVariable("id") int id) {
        return userDetailsService.getUserDetails(id);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void deleteUserDetails(@PathVariable("id") int id) {
        userDetailsService.deleteUserDetails(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<UserDetails> getUserDetailss() {
        return userDetailsService.getAllUserDetails();
    }

}