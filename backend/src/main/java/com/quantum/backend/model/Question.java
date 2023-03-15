package com.quantum.backend.model;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Question {
    private String questionText;
    private String questionType;
    private ArrayList<QuestionInput> answerChoices;
    private String fileUploadName; 
    private boolean isRequired;
}

