package com.quantum.backend.service;

import java.util.List;
import java.util.Optional;

import com.quantum.backend.exception.RequestErrorException;
import com.quantum.backend.exception.ResourceNotFoundException;
import com.quantum.backend.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

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

    public List<User> getUsersByUserType(String userType) throws ResourceNotFoundException{
        List<User> users = userRepo.findByUserType(userType);
        if(users.size() == 0){
            throw new ResourceNotFoundException("User", "userType", userType);
        }
        return users;
    }

    public Optional<User> getUserById(String userId) throws ResourceNotFoundException{
        Optional<User> userData = userRepo.findById(userId);
        if(!userData.isPresent()){
            throw new ResourceNotFoundException("User", "userId", userId);
        }
        return userData;
    }

    public User createUser(User user) throws IllegalArgumentException, RequestErrorException{
        if(userRepo.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("The username has already been taken");
        }
        
        if (userRepo.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("The email has already been taken");
        }

        try{
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
        }
        catch(Exception e){
            throw new RequestErrorException("create", "User", e.getMessage());
        }
        return user;
    }

    public Vendor createVendor(Vendor vendor) throws IllegalArgumentException, RequestErrorException{
        if(userRepo.existsByUsername(vendor.getUsername())){
            throw new IllegalArgumentException("The username has already been taken");
        }
        
        if (userRepo.existsByEmail(vendor.getEmail())){
            throw new IllegalArgumentException("The email has already been taken");
        }

        try{
            vendor.setPassword(encoder.encode(vendor.getPassword()));
            userRepo.save(vendor);
        }
        catch(Exception e){
            throw new RequestErrorException("create", "Vendor", e.getMessage());
        }
        return vendor;
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

    // when admin/approver updates other user info (excluding password)
    public User updateOtherUser(String userId, User userUpdate){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            User userData = user.get();
            userData.setUserType(userUpdate.getUserType());
            userData.setUsername(userUpdate.getUsername());
            userData.setEmail(userUpdate.getEmail());
            userRepo.save(userData);
            return userData;
        }
        return null;
    }

    // when admin/approver updates other vendor info (excluding password)
    public Vendor updateOtherVendor(String userId, Vendor vendorUpdate){
        Optional<User> user = userRepo.findById(userId);
        if(user.isPresent()){
            Vendor vendorData = (Vendor) user.get();
            vendorData.setUserType(vendorUpdate.getUserType());
            vendorData.setUsername(vendorUpdate.getUsername());
            vendorData.setEmail(vendorUpdate.getEmail());
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
