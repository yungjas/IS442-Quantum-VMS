package com.quantum.backend.service;

import java.util.List;
import java.util.Optional;

import com.quantum.backend.model.*;
import org.springframework.stereotype.Service;
import com.quantum.backend.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public Optional<User> getUserById(String userId){
        return userRepo.findById(userId);
    }
}
