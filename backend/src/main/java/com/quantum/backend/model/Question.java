package com.quantum.backend.model;
import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "formBuilder")
public class Question {
    @Id
    private String questionId;
    private String questionText;
    private String questionType;
    private String questionSectionName; // e.g. Part I: Participation in Safety, can be null if question don't have section
    //@DBRef
    private List<QuestionInput> answerChoices;
    private boolean isRequired;
}

