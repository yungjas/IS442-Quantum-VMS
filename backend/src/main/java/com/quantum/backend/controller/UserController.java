package com.quantum.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.backend.model.User;
import com.quantum.backend.service.UserService;

@RestController
@CrossOrigin
@RequestMapping(path="api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("{userId}")
    public Optional<User> getUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }

    @PostMapping("create")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("update/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody User user){
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("delete/{userId}")
    public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
    }
}
