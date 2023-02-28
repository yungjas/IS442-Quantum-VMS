package com.quantum.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.quantum.backend.model.Vendor;
import com.quantum.backend.service.implement.VendorServiceImplement;

@SpringBootApplication
@EnableMongoRepositories
public class BackendApplication implements CommandLineRunner	//Remove implements and run() if not using CLI
{	
	public static void main(String[] args) 
	{
		SpringApplication.run(BackendApplication.class, args);	
	}

	//CREATE
    void createVendor() 
    {
        System.out.println("Data creation started...");
//        Vendor u = new Vendor("1", "John");
        System.out.println("Data creation complete...");
    }
    
	public void run(String... args) 
	{
//		createUser();
	}
}
