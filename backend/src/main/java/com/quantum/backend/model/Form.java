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
    
    private String formNo;
    
    private String formName;
    //private String formDescription;
    
    private int revisionNo;
    //private boolean isApproved;
    private String lastEdited;
    
    private String dateSubmitted;

    private boolean isTemplate;
    
    @DBRef
    private User approvedBy;
    
    @DBRef
    private List<Question> questions;
    // private Date revisionDate = new Date();
    // private Date submittedDate;

}

