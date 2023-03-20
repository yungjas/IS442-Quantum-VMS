package com.quantum.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


// A workflow is an approval sequence of one or more questionnaires that must be filled out in order for the system to produce a form. 
// Not all the details for forms have to be provided by vendors.
@Document(collection="workflow")
@Getter
@Setter
public class Workflow {
    @Id
    private String workflowId;

    private String workflowName;

    // form in a workflow need to be done by a certain date, not placed in form model as maybe one form used in different workflow may have different deadlines
    private String deadline;

    // a workflow can have multiple users assigned to it
    @DBRef
    private List<User> assignedUsers; // update branch to resolve User cannot be defined to a type

    @DBRef
    private Form form; // update branch to resolve Form cannot be defined to a type

    // private String adminId;

    // private String vendorId;


    // questionnaires required for the workflow, with number representing the order
//    private HashMap<Integer, Questionnaire> questionnaires;
}
