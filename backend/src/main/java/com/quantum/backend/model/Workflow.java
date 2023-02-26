package com.quantum.backend.model;

import org.springframework.data.annotation.Id;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;


// A workflow is an approval sequence of one or more questionnaires that must be filled out in order for the system to produce a form. 
// Not all the details for forms have to be provided by vendors.
@Getter
@Setter
public class Workflow {
    @Id
    private String workflowID;

    private String adminID;

    private String vendorID;

    // questionnaires required for the workflow, with number representing the order
    private HashMap<Integer, Questionnaire> questionnaires;
}
