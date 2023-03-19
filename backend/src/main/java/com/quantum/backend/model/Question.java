package com.quantum.backend.model;
import java.util.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Question {
    private String questionText;
    private String questionType;
    @DBRef
    private ArrayList<QuestionInput> answerChoices;
    private boolean isRequired;
}

