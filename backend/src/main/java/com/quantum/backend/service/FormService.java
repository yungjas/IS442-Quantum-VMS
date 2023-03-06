package com.quantum.backend.service;

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

    public Form createWorkflow(Form form){
        formRepo.save(form);
        return form;
    }

    public Form updateForm(String formId, Form formUpdate){
        Optional<Form> form = formRepo.findById(formId);
        if(form.isPresent()){
            Form formOriginal = form.get();
            formOriginal.setRevisionDate(formUpdate.getRevisionDate());
            formOriginal.setFormInfo(formUpdate.getFormInfo());
            formRepo.save(formOriginal);
            return formOriginal;
        }
        return null;
    }
    
    public Form deleteForm(String formId){
        Optional<Form> form = formRepo.findByFormId(formId);
        if(form.isPresent()){
            Form formData = form.get();
            formRepo.delete(formData);
            return formData;
        }
        return null;
    }

}
