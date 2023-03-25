package com.quantum.backend.controller;
import com.quantum.backend.exception.ResourceNotFoundException;
import com.quantum.backend.model.*;
import com.quantum.backend.service.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin
@RequestMapping("api/forms")
public class FormController {    
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> allForms = formService.getAllForms();
        if (allForms.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allForms, HttpStatus.OK);
    }

    @GetMapping("all/templates")
    public ResponseEntity<List<Form>> getAllTemplateForms() {
        List<Form> allTemplateForms = formService.getAllTemplateForms();
        if (allTemplateForms.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allTemplateForms, HttpStatus.OK);
    }

    @GetMapping("{formId}")
    public ResponseEntity<Object> getFormById(@PathVariable String formId){
        Optional<Form> formData = null;
        try{
            formData = formService.getFormById(formId);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(formData.get(), HttpStatus.OK);
    }

    @GetMapping("formNo/{formNo}")
    public ResponseEntity<Object> getFormByNo(@PathVariable String formNo){
        Optional<Form> formData = null;
        try{
            formData = formService.getFormByNo(formNo);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(formData.get(), HttpStatus.OK);
    }

    // @PostMapping("createForm")
    // public ResponseEntity<Form> createForm(@RequestBody Form form) {
    //     try {
    //         Form createdForm = formService.createForm(form);
    //         if (createdForm != null) {
    //             return new ResponseEntity<>(createdForm, HttpStatus.OK);
    //         }
    //         return new ResponseEntity<>(HttpStatus.CONFLICT);
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @PostMapping("create_template")
    public ResponseEntity<Object> createTemplateForm(@RequestBody Form form){
        Form createdFormTemplate = null;
        try{
            createdFormTemplate = formService.createTemplateForm(form);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdFormTemplate, HttpStatus.OK) ;
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<Object> createForm(@RequestBody Form formTemplate) {
        Form createdForm = null;
        try {
            createdForm = formService.createForm(formTemplate);
             // if (createdForm != null) {
            //     // Create a map to store the form data
            //     Map<String, Object> formData = new HashMap<>();
            //     formData.put("formId", createdForm.getFormId());
            //     formData.put("formNo", createdForm.getFormNo());
            //     formData.put("formName", createdForm.getFormName());
            //     formData.put("revisionNo", createdForm.getRevisionNo());
            //     formData.put("lastEdited", createdForm.getLastEdited());
            //     formData.put("dateSubmitted", createdForm.getDateSubmitted());
            //     //formData.put("formDescription", createdForm.getFormDescription());

            //     // Create a list of questions
            //     List<Map<String, Object>> questionList = new ArrayList<>();
            //     for (Question question : createdForm.getQuestions()) {
            //         // Create a map to store the question data
            //         Map<String, Object> questionData = new HashMap<>();
            //         questionData.put("questionText", question.getQuestionText());
            //         questionData.put("questionType", question.getQuestionType());
            //         questionData.put("required", question.isRequired());

            //         // If the question is multiple choice, add the answer choices
            //         if (question.getQuestionType().equals("Multiple choice")) {
            //             questionData.put("answerChoices", question.getAnswerChoices());
            //         }

            //         // If the question is a file upload, add the file upload name
            //         // if (question.getQuestionType().equals("File upload")) {
            //         //     questionData.put("fileUploadName", question.getFileUploadName());
            //         // }

            //         // Add the question data to the question list
            //         questionList.add(questionData);

            //     }
            //     formData.put("questions", questionList);
            //     return new ResponseEntity<>(formData, HttpStatus.OK);
            // }
            //return new ResponseEntity<>(HttpStatus.CONFLICT);   
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(createdForm, HttpStatus.OK) ;
    }

    @PutMapping("approve/{formId}")
    @PreAuthorize("hasRole('APPROVER')")
    public ResponseEntity<Object> approveForm(@PathVariable String formId, @RequestBody Form form){
        Form approveForm = null;
        try{
            approveForm = formService.approveForm(formId, form);
        }
        catch(ResourceNotFoundException re){
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(approveForm, HttpStatus.OK);
    }

    @PutMapping("update/{formId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<Object> updateForm(@PathVariable String formId, @RequestBody Form form){
        Form formUpdate = null;
        try{
            formUpdate = formService.updateForm(formId, form);
        }
        catch(ResourceNotFoundException re){
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(formUpdate, HttpStatus.OK);
    }

    @DeleteMapping("delete/{formId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('APPROVER')")
    public ResponseEntity<Object> deleteForm(@PathVariable String formId) {
        try {
            // if (formService.deleteForm(formId) == null) {
            //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            // } else {
            //     return new ResponseEntity<>(HttpStatus.OK);
            // }
            formService.deleteForm(formId);
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
