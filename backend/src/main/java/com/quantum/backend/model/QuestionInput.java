package com.quantum.backend.model;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionInput {
    private String inputId;
    private String inputName;
    private String inputValue;
    private List<UserResponse> userResponses;

}
