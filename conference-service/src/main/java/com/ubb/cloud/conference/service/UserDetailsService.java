package com.ubb.cloud.conference.service;

import com.ubb.cloud.conference.model.UserDetails;
import com.ubb.cloud.conference.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsService {

    private UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public void saveUser(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    public UserDetails getUserDetails(int id) {
        Optional<UserDetails> userDetails = userDetailsRepository.findById(id);
        return userDetails.orElseGet(UserDetails::new);
    }

    public void deleteUserDetails(int id) {
        userDetailsRepository.deleteById(id);
    }

    public List<UserDetails> getAllUserDetails() {
        return userDetailsRepository.findAll();
    }

}
