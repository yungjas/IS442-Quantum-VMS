package com.quantum.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document("user")
@Getter
@Setter
public class User 
{
    @Id
    private String userId;

    private RoleType userType;

    private String username;

    private String password;

    private String email;

	public User(RoleType userType, String username, String password, String email) 
	{
		super();
		this.userType = userType;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public String getUserId()
	{
		return userId;
	}

	@Override
	public String toString()
	{
		return "User [userId=" + userId + ", userType=" + userType + ", username=" + username + ", password=" + password
				+ ", email=" + email + "]";
	}       
}
