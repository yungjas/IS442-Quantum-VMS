package com.quantum.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

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

    private String adminId;

    private String vendorId;

    //forms required for the workflow, with number representing the order
    private HashMap<Integer, Form> questionnaires;
}
