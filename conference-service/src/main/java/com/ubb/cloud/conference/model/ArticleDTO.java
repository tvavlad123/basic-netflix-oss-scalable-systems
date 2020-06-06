package com.ubb.cloud.conference.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArticleDTO {

    private int id;

    private String title;

    private String domain;

    private UserDTO author;
}