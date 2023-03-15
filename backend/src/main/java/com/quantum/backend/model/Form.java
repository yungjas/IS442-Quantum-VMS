package com.quantum.backend.model;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "form")

public class Form {
    private String formName;
    private String formId;
    private String formDescription;
    private List<Question> questions;
    private Date revisionDate = new Date();
    private Date submittedDate;
    private Map<String, Object> formInfo;

}

