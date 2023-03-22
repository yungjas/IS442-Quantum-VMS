package com.quantum.backend.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.quantum.backend.exception.RequestErrorException;
import com.quantum.backend.exception.ResourceNotFoundException;
import com.quantum.backend.model.Form;
import com.quantum.backend.model.Question;
import com.quantum.backend.model.User;
import com.quantum.backend.model.UserResponse;
import com.quantum.backend.repository.FormBuilderRepository;
import com.quantum.backend.repository.FormRepository;
import com.quantum.backend.repository.UserRepository;
import com.quantum.backend.repository.UserResponseRepository;

@Service
public class UserResponseService {
    private final UserResponseRepository userResponseRepo;
    private final FormRepository formRepository;
    private final FormBuilderRepository formBuilderRepository;
    private final UserRepository userRepository;

    public UserResponseService(UserResponseRepository userResponseRepo, FormRepository formRepository, FormBuilderRepository formBuilderRepository, UserRepository userRepository){
        this.userResponseRepo = userResponseRepo;
        this.formRepository = formRepository;
        this.formBuilderRepository = formBuilderRepository;
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUserResponse(){
        return userResponseRepo.findAll();
    }

    public Optional<UserResponse> getUserResponseById(String userResponseId) throws ResourceNotFoundException{
        Optional<UserResponse> userResponseData = userResponseRepo.findById(userResponseId);
        if(!userResponseData.isPresent()){
            throw new ResourceNotFoundException("UserResponse", "userResponseId", userResponseId);
        }
        return userResponseData;
    }

    public Map<String, Object> getFormQuestionResponse(String userResponseId){
        Optional<UserResponse> userResponseData = userResponseRepo.findById(userResponseId);
        if(!userResponseData.isPresent()){
            throw new ResourceNotFoundException("UserResponse", "userResponseId", userResponseId);
        }
        UserResponse userResponse = userResponseData.get();
        
        // create a map to store form data
        Map<String, Object> formData = new HashMap<>();

        User user = userRepository.findById(userResponse.getUserId()).get();
        Form form = formRepository.findById(userResponse.getFormId()).get();
        Question question = formBuilderRepository.findById(userResponse.getQnId()).get();

        formData.put("userId", user.getUserId());
        formData.put("username", user.getUsername());
        formData.put("formId", form.getFormId());
        formData.put("formNo", form.getFormNo());
        formData.put("formName", form.getFormName());
        formData.put("revisionNo", form.getRevisionNo());
        formData.put("lastEdited", form.getLastEdited());
        formData.put("dateSubmitted", form.getDateSubmitted());
        formData.put("question", question);
        formData.put("questionResponse", userResponse.getQuestionResponse());

        return formData;
    }

    public UserResponse createUserResponse(UserResponse userResponse) throws RequestErrorException{
        try{
            userResponseRepo.save(userResponse);
        }
        catch(Exception e){
            throw new RequestErrorException("create", "UserResponse", e.getMessage());
        }
        return userResponse;
    }
    
    public UserResponse updateUserResponse(String userResponseId, UserResponse userResponseUpdate) throws ResourceNotFoundException, RequestErrorException{
        Optional<UserResponse> userResponse = userResponseRepo.findById(userResponseId);
        UserResponse userResponseData = null;

        if(!userResponse.isPresent()){
            throw new ResourceNotFoundException("UserResponse", "userResponseId", userResponseId);
        }

        try{
            userResponseData = userResponse.get();
            userResponseData.setUserId(userResponseUpdate.getUserId());
            userResponseData.setFormId(userResponseUpdate.getFormId());
            userResponseData.setQnId(userResponseUpdate.getQnId());
            userResponseData.setQuestionResponse(userResponseUpdate.getQuestionResponse());
            userResponseRepo.save(userResponseData);
        }
        catch(Exception e){
            throw new RequestErrorException("update", "UserResponse", e.getMessage());
        }
        return userResponseData;
    }

    public void deleteUserResponse(String userResponseId) throws ResourceNotFoundException, RequestErrorException {
        Optional<UserResponse> userResponse = userResponseRepo.findById(userResponseId);
        UserResponse userResponseData = null;

        if(!userResponse.isPresent()){
            throw new ResourceNotFoundException("UserResponse", "userResponseId", userResponseId);
        }
        try{
            userResponseData = userResponse.get();
            userResponseRepo.delete(userResponseData);
        }
        catch(Exception e){
            throw new RequestErrorException("delete", "UserResponse", e.getMessage());
        }
    }
}
