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

    @PostMapping("/add-question")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        // Add code to save the question to the database
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }

    @PostMapping("/add-textbox")
    public ResponseEntity<Question> addTextbox(@RequestBody String label) {
        Question textbox = new Question();
        textbox.setQuestionType("textbox");
        // textbox.setQuestionLabel(label);
        // Add code to save the question to the database
        return new ResponseEntity<>(textbox, HttpStatus.CREATED);
    }
}
