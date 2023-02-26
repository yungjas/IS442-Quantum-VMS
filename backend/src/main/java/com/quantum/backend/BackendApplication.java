package com.quantum.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.quantum.model.User;
import com.quantum.repo.UserRepo;

@SpringBootApplication
@EnableMongoRepositories
public class BackendApplication implements CommandLineRunner	//Remove implements and run() if not using CLI
{	
	@Autowired
    UserRepo userRepo;	

	public static void main(String[] args) 
	{
		SpringApplication.run(BackendApplication.class, args);
		
		
	}

	//CREATE
    void createUser() {
        System.out.println("Data creation started...");
        
        User u = new User("1", "John");
        System.out.println(u.getName());
//        userRepo.save(u);
//        userRepo.save(new User("1", "John"));
//        userRepo.save(new User("2", "Mary"));
        System.out.println("Data creation complete...");
    }	
	public void run(String... args) {
		createUser();
	}
}
