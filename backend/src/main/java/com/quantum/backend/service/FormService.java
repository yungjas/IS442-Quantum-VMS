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
    private final FormBuilderRepository formBuilderRepo;

    public FormService(FormRepository formRepo, UserRepository userRepo, FormBuilderRepository formBuilderRepo){
        this.formRepo = formRepo;
        this.userRepo = userRepo;
        this.formBuilderRepo = formBuilderRepo;
    }

    public List<Form> getAllForms(){
        return formRepo.findAll();
    }

    public List<Form> getAllTemplateForms(){
        return formRepo.findAllFormTemplates();
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

    public Form createTemplateForm(Form form) throws RequestErrorException{
        Form formTemplate = new Form();
        if(form.isTemplate()){
            try{
                formTemplate.setFormNo(form.getFormNo());
                formTemplate.setFormName(form.getFormName());
                formTemplate.setRevisionNo(form.getRevisionNo());
                formTemplate.setLastEdited(form.getLastEdited());
                formTemplate.setTemplate(form.isTemplate());
                formTemplate.setStatus("");
                saveFormQuestions(form);
                formTemplate.setQuestions(form.getQuestions());
                formRepo.save(formTemplate);
            }
            catch(Exception e){
                throw new RequestErrorException("create form template", "Form", e.getMessage());
            }
        }
        return formTemplate;
    }

    // create form based on a template
    // allow admins to either pick from a list of qns (maybe dropdown box showing a list of qns)
    // or admins can create a new set of qns
    public Form createForm(Form formTemplate) throws RequestErrorException{
        Form actualForm = new Form();
        try{
            actualForm.setFormNo(formTemplate.getFormNo());
            actualForm.setFormName(formTemplate.getFormName());
            actualForm.setRevisionNo(formTemplate.getRevisionNo());
            actualForm.setLastEdited(formTemplate.getLastEdited());
            // since the form is just created, shouldn't have a submission date
            actualForm.setDateSubmitted(null); 
            // template should be false since this form is generated from a template
            actualForm.setTemplate(false);
            actualForm.setStatus("waiting vendor");
            // since this form is just created, shouldn't have anyone to approve yet
            actualForm.setApprovedBy(null); 
            // duplicate questions
            actualForm.setQuestions(formTemplate.getQuestions());
            List<Question> duplicateQns = duplicateQuestion(actualForm);
            // set the questions to the ones that are duplicated
            actualForm.setQuestions(duplicateQns);
            
            formRepo.save(actualForm);
        }
        catch(Exception e){
            throw new RequestErrorException("create", "Form", e.getMessage());
        }
        return actualForm;
    }

    public Form updateStatus(String formId, String status) throws RequestErrorException{
        Optional<Form> currentForm = formRepo.findById(formId);
        Form currentFormData = null;

        if(!currentForm.isPresent()){
            throw new ResourceNotFoundException("Form", "formId", formId);
        }
        
        try{
            currentFormData = currentForm.get();
            currentFormData.setStatus(status);
            formRepo.save(currentFormData);
        }
        catch(Exception e){
            throw new RequestErrorException("approve", "Form", e.getMessage());
        }
        
        return currentFormData;
    }

    public Form approveForm(String formId, Form form) throws RequestErrorException{
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

    public Form unapproveForm(String formId, Form form) throws RequestErrorException{
        Optional<Form> currentForm = formRepo.findById(formId);
        Form currentFormData = null;

        if(!currentForm.isPresent()){
            throw new ResourceNotFoundException("Form", "formId", formId);
        }
        
        try{
            currentFormData = currentForm.get();
            currentFormData.setApprovedBy(null);
            formRepo.save(currentFormData);
        }
        catch(Exception e){
            throw new RequestErrorException("approve", "Form", e.getMessage());
        }
        
        return currentFormData;
    }

    // allow admins to either pick from a list of qns (maybe dropdown box showing a list of qns)
    // or admins can create a new set of qns to update current form
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
            formData.setStatus(formUpdate.getStatus());
            saveFormQuestions(formUpdate);
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

    private List<Question> duplicateQuestion(Form form){
        List<Question> questions = new ArrayList<>();
        for(Question qn: form.getQuestions()){
            Question newQuestion = new Question();
            newQuestion.setQuestionText(qn.getQuestionText());
            newQuestion.setQuestionType(qn.getQuestionType());
            newQuestion.setQuestionSectionName(qn.getQuestionSectionName());
            newQuestion.setAnswerChoices(qn.getAnswerChoices());
            newQuestion.setRequired(qn.isRequired());
            newQuestion.setTemplate(false);
            formBuilderRepo.save(newQuestion);
            questions.add(newQuestion);
        }

        return questions;
    }

    private void saveFormQuestions(Form form){
        // loop through each question and check if it is in db, if not save to formbuilder table
        for(Question qn: form.getQuestions()){
            if(qn.getQuestionId() == null || qn.getQuestionId().isEmpty()){
                formBuilderRepo.save(qn);
            }
        }
    }
}
