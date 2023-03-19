package com.quantum.backend.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "form")

public class Form {
    @Id
    private String formId;
    private String formName;
    private String formNo;
    private String formDescription;
    @DBRef
    private List<Question> questions;
    private Date revisionDate = new Date();
    private Date submittedDate;
    private User approvedBy;

}

