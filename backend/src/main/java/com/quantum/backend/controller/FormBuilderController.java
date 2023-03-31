package com.quantum.backend.controller;
import com.quantum.backend.exception.ResourceNotFoundException;
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
@CrossOrigin
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

    @GetMapping("/all_templates")
    public ResponseEntity<List<Question>> getAllQuestionTemplates(){
        List<Question> allTemplateQns = formBuilderService.getAllTemplateQuestions();
        if(allTemplateQns.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allTemplateQns, HttpStatus.OK);
    }

    @GetMapping("/form_edit_qns/{formId}")
    public ResponseEntity<List<Question>> getCurrentFormQnsAndTemplateQns(@PathVariable String formId){
        List<Question> resultQns = formBuilderService.getRemainingTemplateQns(formId);
        if(resultQns.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultQns, HttpStatus.OK);
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

    @PutMapping("/edit-question/{questionId}")
    public ResponseEntity<Object> updateQuestion(@PathVariable String questionId, @RequestBody Question question) {
        Question questionUpdated = null;
        try {
            questionUpdated = formBuilderService.updateQuestion(questionId, question);
        } catch(IllegalArgumentException ill){
            return new ResponseEntity<>(ill.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(questionUpdated, HttpStatus.CREATED);
    }

    
    @DeleteMapping("delete/{questionId}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable String questionId) {
        Question questionDeleted = null;
        try{
            questionDeleted = formBuilderService.deleteQuestion(questionId);            
        }
        catch(ResourceNotFoundException re){
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
