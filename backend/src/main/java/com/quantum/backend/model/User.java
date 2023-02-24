package com.quantum.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User {
    @Id
    private String userId;

    private char userType;

    private String username;

    private String password;

    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public char getUserType() {
        return userType;
    }

    public void setUserType(char userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return email;
    }

    public void setEmailAddress(String email) {
        this.email = email;
    }
}
