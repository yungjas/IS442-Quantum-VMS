package com.quantum.backend.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quantum.backend.exception.ResourceNotFoundException;
import com.quantum.backend.model.UserResponse;
import com.quantum.backend.service.UserResponseService;


@RestController
@CrossOrigin
@RequestMapping("api/response")
public class UserResponseController {
    private final UserResponseService userResponseService;

    public UserResponseController(UserResponseService userResponseService){
        this.userResponseService = userResponseService;
    }

    @GetMapping("all")
    public ResponseEntity<List<UserResponse>> getAllUserResponse(){
        List<UserResponse> allResponse = userResponseService.getAllUserResponse();
        if(allResponse.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allResponse, HttpStatus.OK);
    }

    @GetMapping("{userResponseId}")
    public ResponseEntity<Object> getUserResponseById(@PathVariable String userResponseId){
        Optional<UserResponse> userResponseData = null;
        try{
            userResponseData = userResponseService.getUserResponseById(userResponseId);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userResponseData.get(), HttpStatus.OK);
    }

    @GetMapping("all_responses/{userId}")
    public ResponseEntity<Object> getFormQuestionResponses(@PathVariable String userId){
        List<Object> userResponses = null;
        try{
            userResponses = userResponseService.getFormQuestionResponses(userId);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @GetMapping("form_response/{userId}/{formId}")
    public ResponseEntity<Object> getFormResponse(@PathVariable String userId, @PathVariable String formId){
        Map<String, Object> userResponses = null;
        try{
            userResponses = userResponseService.getFormResponse(userId, formId);
        }
        catch(Exception e){
            new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Object> createResponse(@RequestBody UserResponse userResponse){
        UserResponse createdResponse = null;
        try{
            createdResponse = userResponseService.createUserResponse(userResponse);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdResponse, HttpStatus.OK) ;
    }

    @PutMapping("update/{userResponseId}")
    public ResponseEntity<Object> updateUserResponse(@PathVariable String userResponseId, @RequestBody UserResponse userResponse){
        UserResponse userResponseUpdate = null;
        try{
            userResponseUpdate = userResponseService.updateUserResponse(userResponseId, userResponse);
        }
        catch(ResourceNotFoundException re){
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(userResponseUpdate, HttpStatus.OK);
    }

    @DeleteMapping("delete/{userResponseId}")
    public ResponseEntity<Object> deleteForm(@PathVariable String userResponseId) {
        try {
            userResponseService.deleteUserResponse(userResponseId);
        }
        catch(ResourceNotFoundException re){
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        } 
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
