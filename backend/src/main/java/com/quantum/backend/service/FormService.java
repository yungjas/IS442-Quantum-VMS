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

    public Form createForm(Form form){
        formRepo.save(form);
        return form;
    }

    public Form updateForm(String formId, Form formUpdate) throws ResourceNotFoundException{
        Optional<Form> form = formRepo.findById(formId);
        if(form.isPresent()){
            Form formOriginal = form.get();
            formOriginal.setRevisionDate(formUpdate.getRevisionDate());
            formRepo.save(formOriginal);
            return formOriginal;
        }
        throw new ResourceNotFoundException("Form", "formId", formId);
    }
    
    public Form deleteForm(String formId) throws ResourceNotFoundException{
        Optional<Form> form = formRepo.findByFormId(formId);
        if(form.isPresent()){
            Form formData = form.get();
            formRepo.delete(formData);
            return formData;
        }
        throw new ResourceNotFoundException("Form", "formId", formId);
    }

}
