package com.ubb.cloud.conference.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TalkDetailDTO {

    private Integer id;

    private String title;

    private Timestamp startTime;

    private Timestamp endTime;

    private RoomDTO room;
}