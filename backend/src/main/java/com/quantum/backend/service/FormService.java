package com.quantum.backend.service;

import com.quantum.backend.exception.RequestErrorException;
import com.quantum.backend.exception.ResourceNotFoundException;
import com.quantum.backend.model.*;
import com.quantum.backend.repository.*;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class FormService {
    private final FormRepository formRepo;

    public FormService(FormRepository formRepo){
        this.formRepo = formRepo;
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

    public Form updateForm(String formId, Form formUpdate) throws ResourceNotFoundException{
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
    
    public void deleteForm(String formId) throws ResourceNotFoundException{
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
