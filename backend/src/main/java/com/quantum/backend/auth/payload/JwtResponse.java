package com.quantum.backend.auth.payload;

import java.util.List;

import com.quantum.backend.model.RoleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String tokenType = "Bearer";
    private String userId;
    private List<String> roles;
    private String username;
    private String email;

    public JwtResponse(String token, String userId, String username, String email, List<String> roles){
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
