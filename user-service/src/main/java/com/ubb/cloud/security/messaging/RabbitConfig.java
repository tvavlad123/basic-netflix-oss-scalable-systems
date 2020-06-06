package com.ubb.cloud.security.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_USERS = "users-queue";
    public static final String EXCHANGE_USERS = "users-exchange";

    @Bean
    Queue usersQueue() {
        return QueueBuilder.nonDurable(QUEUE_USERS).build();
    }

    @Bean
    Exchange usersExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_USERS).build();
    }

    @Bean
    Binding binding(Queue usersQueue, TopicExchange usersExchange) {
        return BindingBuilder.bind(usersQueue).to(usersExchange).with(QUEUE_USERS);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}