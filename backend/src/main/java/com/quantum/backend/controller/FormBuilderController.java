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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/form-builder")
public class FormBuilderController {
    
    private final FormBuilderService formBuilderService;

    public FormBuilderController(FormBuilderService formBuilderService){
        this.formBuilderService = formBuilderService;
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getQuestions() {
        List<Question> allQns = formBuilderService.getAllQuestions();
        if(allQns.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allQns, HttpStatus.OK);
    }

    @PostMapping("/add-question")
    public ResponseEntity<Object> createQuestion(@RequestBody Question question) {
        Question questionCreated = null;
        try {
            questionCreated = formBuilderService.addQuestion(question);
        } catch(IllegalArgumentException ill){
            return new ResponseEntity<>(ill.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(questionCreated, HttpStatus.CREATED);
    }
}
