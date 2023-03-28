package com.quantum.backend.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // just gets the data without mapping
    public Optional<UserResponse> getUserResponseById(String userResponseId) throws ResourceNotFoundException{
        Optional<UserResponse> userResponseData = userResponseRepo.findById(userResponseId);
        if(!userResponseData.isPresent()){
            throw new ResourceNotFoundException("UserResponse", "userResponseId", userResponseId);
        }
        return userResponseData;
    }

    // get a user's responses to a form
    public Map<String, Object> getFormResponse(String userId, String formId){
        List<UserResponse> userResponses = userResponseRepo.findFormResponse(userId, formId);
        
        Map<String, Object> result = new HashMap<>();

        // returns the form if user did not respond
        if(userResponses == null || userResponses.isEmpty()){
            Form formActual = formRepository.findById(formId).get();
            result.put("form", formActual);
            return result;
        }

        Map<String, Object> formData = new HashMap<>();
        Map<String, Object> userData = new HashMap<>();
        List<Object> qnResponseInfoList = new ArrayList<>();

        for(UserResponse response: userResponses){
            Map<String, Object> qnData = new HashMap<>();
            User user = userRepository.findById(response.getUserId()).get();
            Form form = formRepository.findById(response.getFormId()).get();
            Question question = formBuilderRepository.findById(response.getQnId()).get();
            
            if(!result.containsKey("user")){
                userData.put("userId", user.getUserId());
                userData.put("username", user.getUsername());
                userData.put("email", user.getEmail());
                result.put("user", userData);
            }

            if(!result.containsKey("form")){
                formData.put("formId", form.getFormId());
                formData.put("formNo", form.getFormNo());
                formData.put("formName", form.getFormName());
                formData.put("revisionNo", form.getRevisionNo());
                formData.put("lastEdited", form.getLastEdited());
                formData.put("dateSubmitted", form.getDateSubmitted());
                result.put("form", formData);
            }

            qnData.put("questionId", question.getQuestionId());
            qnData.put("questionText", question.getQuestionText());
            qnData.put("questionType", question.getQuestionType());
            qnData.put("questionSectionName", question.getQuestionSectionName());
            qnData.put("answerChoices", question.getAnswerChoices());
            qnData.put("questionResponse", response.getQuestionResponse());
            qnResponseInfoList.add(qnData);

            result.put("questionResponseInfo", qnResponseInfoList);
        }

        return result;
    }

    // getting all forms and questions that the user has responded to
    public List<Object> getFormQuestionResponses(String userId){
        List<UserResponse> userResponses = userResponseRepo.findResponsesByUserId(userId);
        List<Object> resultList = new ArrayList<>();
        for(UserResponse response: userResponses){
            // create a map to store form data
            Map<String, Object> formData = new HashMap<>();

            User user = userRepository.findById(response.getUserId()).get();
            Form form = formRepository.findById(response.getFormId()).get();
            Question question = formBuilderRepository.findById(response.getQnId()).get();

            // just getting form information and the question that was answered
            formData.put("userId", user.getUserId());
            formData.put("username", user.getUsername());
            formData.put("email", user.getEmail());
            formData.put("formId", form.getFormId());
            formData.put("formNo", form.getFormNo());
            formData.put("formName", form.getFormName());
            formData.put("revisionNo", form.getRevisionNo());
            formData.put("lastEdited", form.getLastEdited());
            formData.put("dateSubmitted", form.getDateSubmitted());
            formData.put("question", question);
            formData.put("questionResponse", response.getQuestionResponse());
            resultList.add(formData);
        }
        return resultList;
    }

    // logs user responses to a question when user clicks on submit
    public UserResponse createUserResponse(UserResponse userResponse) throws RequestErrorException, ResourceNotFoundException{
        Optional<Form> formData = formRepository.findById(userResponse.getFormId());
        if (formData.isPresent()) {
            Form form = formData.get();
            // only allow saving of form responses if form is not a template
            if (form.isTemplate() == false) {
                try {
                    // get current logged in user so that we know who's submitting the response
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    String name = auth.getName();
                    Optional<User> user = userRepository.findByUsername(name);
                    userResponse.setUserId(user.get().getUserId());

                    // set the submitted date
                    DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String currentDate = sourceFormat.format(new Date());
                    form.setDateSubmitted(currentDate); 

                    userResponseRepo.save(userResponse);
                    formRepository.save(form);
                    return userResponse;
                } 
                catch (Exception e) {
                    throw new RequestErrorException("create", "UserResponse", e.getMessage());
                }
            } 
            else {
                throw new RequestErrorException("create", "UserResponse", "Cannot save form responses of a form template");
            }
        } 
        else {
            throw new ResourceNotFoundException("Form", "formId", userResponse.getFormId());
        }
    }
    
    // should only let the user update their responses for a question
    public UserResponse updateUserResponse(String userResponseId, UserResponse userResponseUpdate) throws ResourceNotFoundException, RequestErrorException{
        Optional<UserResponse> userResponse = userResponseRepo.findById(userResponseId);
        UserResponse userResponseData = null;

        if(!userResponse.isPresent()){
            throw new ResourceNotFoundException("UserResponse", "userResponseId", userResponseId);
        }

        try{
            userResponseData = userResponse.get();
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
