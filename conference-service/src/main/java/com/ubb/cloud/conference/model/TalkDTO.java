package com.ubb.cloud.conference.model;

import lombok.Data;

@Data
public class TalkDTO {

    private Integer id;

    private String title;

    private String domain;

    private UserDTO author;
}