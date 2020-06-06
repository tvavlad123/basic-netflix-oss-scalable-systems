package com.ubb.cloud.conference.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NewTalkDTO {

    private Timestamp startTime;

    private Timestamp endTime;

    private Integer articleId;
}
