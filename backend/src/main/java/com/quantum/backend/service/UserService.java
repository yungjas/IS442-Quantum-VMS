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

    public List<User> getUsersByUserType(String userType){
        return userRepo.findByUserType(userType);
    }

    public Optional<User> getUserById(String userId){
        return userRepo.findById(userId);
    }

    public User createUser(User user){
        if(!userRepo.existsByUsername(user.getUsername()) && !userRepo.existsByEmail(user.getEmail())){
            userRepo.save(user);
            return user;
        }
        return null;
    }

    public Vendor createVendor(Vendor vendor){
        if(!userRepo.existsByUsername(vendor.getUsername()) && !userRepo.existsByEmail(vendor.getEmail())){
            userRepo.save(vendor);
            return vendor;
        }
        return null;
    }

    public User updateUser(String userId, User userUpdate){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            User userOriginal = user.get();
            userOriginal.setUserType(userUpdate.getUserType());
            userOriginal.setUsername(userUpdate.getUsername());
            userOriginal.setEmail(userUpdate.getEmail());
            userOriginal.setPassword(userUpdate.getPassword());
            userRepo.save(userOriginal);
            return userOriginal;
        }
        return null;
    }

    public User deleteUser(String userId){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            User userData = user.get();
            userRepo.delete(userData);
            return userData;
        }
        return null;
    }
}
