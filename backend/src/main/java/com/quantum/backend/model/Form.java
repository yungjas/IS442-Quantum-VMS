package com.quantum.backend.model;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;


@Document(collection = "form")
public class Form {
    @Id
    private String formId;
    

}
