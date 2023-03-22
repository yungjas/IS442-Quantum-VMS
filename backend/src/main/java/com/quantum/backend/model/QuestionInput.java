package com.quantum.backend.model;
import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Document(collection = "formBuilderInput")
public class QuestionInput { 
    // @Id
    // private String inputId;
    private String inputName; // e.g. Poor
    private String inputValue; // e.g. 1
    @DBRef
    private List<User> userResponses; // store users with the same response

}
