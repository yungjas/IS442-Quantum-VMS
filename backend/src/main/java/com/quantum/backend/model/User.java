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

	public User(RoleType userType, String username, String password, String email) 
	{
		this.userType = userType;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	// public String getUserId() 
	// {
	// 	return userId;
	// }

	// public void setUserId(String userId) 
	// {
	// 	this.userId = userId;
	// }

	// public RoleType getUserType() 
	// {
	// 	return userType;
	// }

	// public void setUserType(RoleType userType) 
	// {
	// 	this.userType = userType;
	// }

	// public String getUsername() 
	// {
	// 	return username;
	// }

	// public void setUsername(String username) 
	// {
	// 	this.username = username;
	// }

	// public String getPassword() 
	// {
	// 	return password;
	// }

	// public void setPassword(String password) 
	// {
	// 	this.password = password;
	// }

	// public String getEmail() 
	// {
	// 	return email;
	// }

	// public void setEmail(String email) 
	// {
	// 	this.email = email;
	// }

	@Override
	public String toString()
	{
		return "User [userId=" + userId + ", userType=" + userType + ", username=" + username + ", password=" + password
				+ ", email=" + email + "]";
	}       
}
