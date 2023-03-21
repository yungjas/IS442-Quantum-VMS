package com.quantum.backend.model;
import java.util.*;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionInput { 
    private String inputId;
    private String inputName;
    private String inputValue;
    @DBRef
    private List<User> userResponses; // store users with the same response

}
