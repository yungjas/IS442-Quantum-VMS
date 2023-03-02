package com.quantum.backend.controller;
import com.quantum.backend.model.*;
import com.quantum.backend.service.*;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@CrossOrigin
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

    @PostMapping("create")
    public ResponseEntity<Form> createWorkflow(@RequestBody Form form) {
        try {
            Form createdForm = formService.createWorkflow(form);
            if (createdForm != null) {
                return new ResponseEntity<>(createdForm, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<Form> updateForm(@PathVariable String formId, @RequestBody Form form){
        Form formUpdate = formService.updateForm(formId, form);
        if (formUpdate == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(formUpdate, HttpStatus.OK);
    }

    @DeleteMapping("delete/{workflowId}")
    public ResponseEntity<HttpStatus> deleteWorkflow(@PathVariable String formId) {
        try {
            if (formService.deleteForm(formId) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
