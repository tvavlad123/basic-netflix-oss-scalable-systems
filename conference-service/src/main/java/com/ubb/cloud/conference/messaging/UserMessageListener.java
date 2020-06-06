package com.ubb.cloud.conference.messaging;

import com.ubb.cloud.conference.model.UserDetails;
import com.ubb.cloud.conference.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserMessageListener {

    private UserDetailsService userDetailsService;

    @Autowired
    public UserMessageListener(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_USERS)
    public void processUserDetails(UserDetails userDetails) {
        log.info("User Received: " + userDetails);
        userDetailsService.saveUser(userDetails);
    }
}
