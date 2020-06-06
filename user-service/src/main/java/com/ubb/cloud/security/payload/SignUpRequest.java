package com.ubb.cloud.security.payload;

import com.ubb.cloud.security.model.Role;
import lombok.Data;
import org.hibernate.validator.constraints.Email;

import java.util.List;

@Data
public class SignUpRequest {

    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;
    private String affiliation;
    private List<Role> roles;
}