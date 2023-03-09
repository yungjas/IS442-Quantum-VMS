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
    private String formID;
    private String formDescription;
    private List<Question> questions;
    private Date revisionDate = new Date();
    private Date submittedDate;
    private Map<String, Object> formInfo;

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormID() {
        return formID;
    }

    public void setFormID(String formID) {
        this.formID = formID;
    }

    public String getFormDescription() {
        return formDescription;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

