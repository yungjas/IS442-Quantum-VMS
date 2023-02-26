package com.quantum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.model.User;
import com.quantum.repo.UserRepo;

@RestController
public class UserController 
{
	@Autowired
	UserRepo repo;
	
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody User u)
	{
		repo.save(u);
		
		return new ResponseEntity<String>("Data Inserted", HttpStatus.OK);
	}
}
