package com.quantum.backend.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.quantum.backend.exception.RequestErrorException;
import com.quantum.backend.exception.ResourceNotFoundException;
import com.quantum.backend.model.Form;
import com.quantum.backend.model.Question;
import com.quantum.backend.repository.*;

@Service
public class FormBuilderService {
    private final FormBuilderRepository formBuilderRepo;
    private final FormRepository formRepo;

    public FormBuilderService(FormBuilderRepository formBuilderRepo, FormRepository formRepo){
        this.formBuilderRepo = formBuilderRepo;
        this.formRepo = formRepo;
    }

    public List<Question> getAllQuestions(){
        return formBuilderRepo.findAll();
    }

    public List<Question> getAllTemplateQuestions(){
        return formBuilderRepo.findAllQnTemplates();
    }

    public Optional<Question> getQuestionById(String questionId) throws ResourceNotFoundException{
        Optional<Question> questionData = formBuilderRepo.findById(questionId);
        if(!questionData.isPresent()){
            throw new ResourceNotFoundException("Question", "questionId", questionId);
        }
        return questionData;
    }

    // filter remaining template questions for admin to add
    public List<Question> getRemainingTemplateQns(String formId){
        List<Question> resultList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        Form form = formRepo.findById(formId).get();
        List<Question> templateQns = formBuilderRepo.findAllQnTemplates();

        for(Question qn: form.getQuestions()){
            tempList.add(qn.getQuestionText());
        }

        for(Question templateQn: templateQns){
            if(!tempList.contains(templateQn.getQuestionText())){
                resultList.add(templateQn);
            }
        }

        return resultList;
    }

    public Question addQuestion(@RequestBody Question question) throws IllegalArgumentException{
        // Add code to save the question to the database
        // if(formBuilderRepo.existsById(question.getQuestionId())){
        //     throw new IllegalArgumentException("This question exists");
        // }
        try {
            formBuilderRepo.save(question);
        } catch (Exception e) {
            throw new RequestErrorException("create", "Question", e.getMessage());
        }
        return question;
    }

    public Question updateQuestion(String questionId, Question questionUpdate) throws IllegalArgumentException{
        Optional<Question> question = formBuilderRepo.findById(questionId);
        Question questionData = null;

        if(!question.isPresent()){
            throw new ResourceNotFoundException("Question", "questionId", questionId);
        } 
        try {
            questionData = question.get();
            questionData.setQuestionText(questionUpdate.getQuestionText());
            questionData.setQuestionType(questionUpdate.getQuestionType());
            questionData.setQuestionSectionName(questionUpdate.getQuestionSectionName());
            questionData.setAnswerChoices(questionUpdate.getAnswerChoices());
            questionData.setTemplate(questionUpdate.isTemplate());
            questionData.setRequired(questionUpdate.isRequired());
            formBuilderRepo.save(questionData);
        } catch(Exception e){
            throw new RequestErrorException("update", "Question", e.getMessage());
        }

        return questionData;
    }   

    public Question deleteQuestion(String questionId) throws ResourceNotFoundException, RequestErrorException{
        Optional<Question> question = formBuilderRepo.findById(questionId);
        Question questionData = null;
        if(!question.isPresent()){
            throw new ResourceNotFoundException("Question", "userId", questionId);
        } try {
            questionData = question.get();
            formBuilderRepo.delete(questionData);
        } catch (Exception e) {
            throw new RequestErrorException("delete", "Question", questionId);
        }
        return questionData;
    }
}
