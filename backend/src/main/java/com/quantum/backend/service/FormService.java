package com.quantum.backend.service;

import com.quantum.backend.exception.RequestErrorException;
import com.quantum.backend.exception.ResourceNotFoundException;
import com.quantum.backend.model.*;
import com.quantum.backend.repository.*;
import java.util.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FormService {
    private final FormRepository formRepo;
    private final UserRepository userRepo;

    public FormService(FormRepository formRepo, UserRepository userRepo){
        this.formRepo = formRepo;
        this.userRepo = userRepo;
    }

    public List<Form> getAllForms(){
        return formRepo.findAll();
    }

    public Optional<Form> getFormById(String formId) throws ResourceNotFoundException{
        Optional<Form> formData = formRepo.findById(formId);
        if(!formData.isPresent()){
            throw new ResourceNotFoundException("Form", "formId", formId);
        }
        return formData;
    }

    public Optional<Form> getFormByNo(String formNo) throws ResourceNotFoundException{
        Optional<Form> formData = formRepo.findByFormNo(formNo);
        if(!formData.isPresent()){
            throw new ResourceNotFoundException("Form", "formNo", formNo);
        }
        return formData;
    }

    public Form createForm(Form form) throws RequestErrorException{
        try{
            formRepo.save(form);
        }
        catch(Exception e){
            throw new RequestErrorException("create", "Form", e.getMessage());
        }
        return form;
    }

    public Form approveForm(String formId, Form form) throws RequestErrorException, RequestErrorException{
        Optional<Form> currentForm = formRepo.findById(formId);
        Form currentFormData = null;

        if(!currentForm.isPresent()){
            throw new ResourceNotFoundException("Form", "formId", formId);
        }
        
        try{
            // get current logged in user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            Optional<User> user = userRepo.findByUsername(name);
            
            if(user.isPresent()){
                currentFormData = currentForm.get();
                currentFormData.setApprovedBy(user.get());
                formRepo.save(currentFormData);
            }
        }
        catch(Exception e){
            throw new RequestErrorException("approve", "Form", e.getMessage());
        }
        
        return currentFormData;
    }

    public Form updateForm(String formId, Form formUpdate) throws RequestErrorException, ResourceNotFoundException{
        Optional<Form> form = formRepo.findById(formId);
        Form formData = null;

        if(!form.isPresent()){
            throw new ResourceNotFoundException("Form", "formId", formId);
        }

        try{
            formData = form.get();
            formData.setFormNo(formUpdate.getFormNo());
            formData.setFormName(formUpdate.getFormName());
            formData.setRevisionNo(formUpdate.getRevisionNo());
            formData.setLastEdited(formUpdate.getLastEdited());
            formData.setDateSubmitted(formUpdate.getDateSubmitted());
            formData.setApprovedBy(formUpdate.getApprovedBy());
            formData.setQuestions(formUpdate.getQuestions());
            formRepo.save(formData);
        }
        catch(Exception e){
            throw new RequestErrorException("update", "Form", e.getMessage());
        }
        return formData;
    }
    
    public void deleteForm(String formId) throws RequestErrorException, ResourceNotFoundException{
        Optional<Form> form = formRepo.findById(formId);
        Form formData = null;
        if(!form.isPresent()){
            throw new ResourceNotFoundException("Form", "formId", formId);
        }
        try{
            formData = form.get();
            formRepo.delete(formData);
        }
        catch(Exception e){
            throw new RequestErrorException("delete", "Form", e.getMessage());
        }       
    }
}
