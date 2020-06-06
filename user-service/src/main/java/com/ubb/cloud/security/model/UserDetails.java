package com.ubb.cloud.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDetails implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String affiliation;
}
