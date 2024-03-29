package com.quantum.backend.model;

import java.util.Map;

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
	
	// able to add more inputs when creating user in the future
	//private Map<String, String> otherInfo;

	public User(RoleType userType, String username, String password, String email) 
	{
		this.userType = userType;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	@Override
	public String toString()
	{
		return "User [userId=" + userId + ", userType=" + userType + ", username=" + username + ", password=" + password
				+ ", email=" + email + "]";
	}       
}
