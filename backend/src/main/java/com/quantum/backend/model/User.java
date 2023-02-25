package com.quantum.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection="user")
@Getter
@Setter
public class User {
    @Id
    private String userId;

    private RoleType userType;

    private String username;

    private String password;

    private String email;
}
