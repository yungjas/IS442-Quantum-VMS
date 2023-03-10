package com.quantum.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.quantum.backend.model.Vendor;
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
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        if(allUsers.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("usertype/{userType}")
    public ResponseEntity<Object> getUsersByUserType(@PathVariable String userType){
        List<User> users = null;
        try{
            users = userService.getUsersByUserType(userType);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable String userId){
        Optional<User> userData = null;
        try{
            userData = userService.getUserById(userId);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userData.get(), HttpStatus.OK);
    }

    @PostMapping("create_user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User userCreated = null;
        try{
            userCreated = userService.createUser(user);
        }
        catch(IllegalArgumentException ill){
            return new ResponseEntity<>(ill.getMessage(), HttpStatus.CONFLICT);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    @PostMapping("create_vendor")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<Object> createVendor(@RequestBody Vendor vendor){
        Vendor vendorCreated = null;
        try{
            vendorCreated = userService.createVendor(vendor);
        }
        catch(IllegalArgumentException ill){
            return new ResponseEntity<>(ill.getMessage(), HttpStatus.CONFLICT);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(vendorCreated, HttpStatus.OK);
    }

    @PutMapping("update_user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user){
        User userUpdate = userService.updateUser(userId, user);
        if(userUpdate == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userUpdate, HttpStatus.OK);
    }

    @PutMapping("update_other_user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<User> updateOtherUser(@PathVariable String userId, @RequestBody User user){
        User otherUserUpdate = userService.updateOtherUser(userId, user);
        if(otherUserUpdate == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(otherUserUpdate, HttpStatus.OK);
    }

    @PutMapping("update_vendor/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<Vendor> updateVendor(@PathVariable String userId, @RequestBody Vendor vendor){
        Vendor vendorUpdate = userService.updateVendor(userId, vendor);
        if(vendorUpdate == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vendorUpdate, HttpStatus.OK);
    }

    @PutMapping("update_other_vendor/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<Vendor> updateOtherVendor(@PathVariable String userId, @RequestBody Vendor vendor){
        Vendor otherVendorUpdate = userService.updateOtherVendor(userId, vendor);
        if(otherVendorUpdate == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(otherVendorUpdate, HttpStatus.OK);
    }

    @DeleteMapping("delete/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String userId){
        try{
            User userDelete = userService.deleteUser(userId);
            if(userDelete == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
        }
    }
}
