package com.quantum.backend.service;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.quantum.backend.exception.RequestErrorException;
import com.quantum.backend.model.Question;
import com.quantum.backend.repository.*;

public class FormBuilderService {
    private final FormBuilderRepository formBuilderRepo;

    public FormBuilderService(FormBuilderRepository formBuilderRepo){
        this.formBuilderRepo = formBuilderRepo;
    }

    public List<Question> getAllQuestions(){
        return formBuilderRepo.findAll();
    }
    public Question addQuestion(@RequestBody Question question) throws IllegalArgumentException{
        // Add code to save the question to the database
        if(formBuilderRepo.existsById(question.getQuestionId())){
            throw new IllegalArgumentException("This question exists");
        }
        try {
            formBuilderRepo.save(question);
        } catch (Exception e) {
            throw new RequestErrorException("create", "Question", e.getMessage());
        }
        return question;
    }

    // @PostMapping("/add-textbox")
    // public ResponseEntity<Question> addTextbox(@RequestBody String label) {
    //     Question textbox = new Question();
    //     textbox.setQuestionType("textbox");
    //     // textbox.setQuestionLabel(label);
    //     // Add code to save the question to the database

    //     return new ResponseEntity<>(textbox, HttpStatus.CREATED);
    // }
}
