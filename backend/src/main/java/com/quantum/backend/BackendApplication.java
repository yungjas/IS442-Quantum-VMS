package com.quantum.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
public class BackendApplication implements CommandLineRunner	//Remove implements and run() if not using CLI
{	
	public static void main(String[] args) 
	{
		SpringApplication.run(BackendApplication.class, args);	
	}
    
	public void run(String... args) 
	{

	}
}
