package com.quantum.backend.service;

import java.util.List;
import java.util.Optional;

import com.quantum.backend.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.quantum.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired
	PasswordEncoder encoder;
    
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
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            return user;
        }
        return null;
    }

    public Vendor createVendor(Vendor vendor){
        if(!userRepo.existsByUsername(vendor.getUsername()) && !userRepo.existsByEmail(vendor.getEmail())){
            vendor.setPassword(encoder.encode(vendor.getPassword()));
            userRepo.save(vendor);
            return vendor;
        }
        return null;
    }

    public User updateUser(String userId, User userUpdate){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            User userData = user.get();
            userData.setUserType(userUpdate.getUserType());
            userData.setUsername(userUpdate.getUsername());
            userData.setEmail(userUpdate.getEmail());
            userData.setPassword(encoder.encode(userUpdate.getPassword()));
            userRepo.save(userData);
            return userData;
        }
        return null;
    }

    public Vendor updateVendor(String userId, Vendor vendorUpdate){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            Vendor vendorData = (Vendor) user.get();
            vendorData.setUserType(vendorUpdate.getUserType());
            vendorData.setUsername(vendorUpdate.getUsername());
            vendorData.setEmail(vendorUpdate.getEmail());
            vendorData.setPassword(encoder.encode(vendorUpdate.getPassword()));
            vendorData.setCompanyName(vendorUpdate.getCompanyName());
            userRepo.save(vendorData);
            return vendorData;
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
